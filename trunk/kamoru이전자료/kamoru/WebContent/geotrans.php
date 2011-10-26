<?php
	class GeoTrans{
		var $srctype;
		var $dsttype;
		var $m_Ind, $m_Es, $m_Esp, $src_m, $dst_m;

		var $EPSLN = 0.0000000001;
		var $m_arMajor = 6378137.0;
		var $m_arMinor = 6356752.3142;

		var $m_arScaleFactor = array();
		var $m_arLonCenter = array();
		var $m_arLatCenter = array();
		var $m_arFalseNorthing = array();
		var $m_arFalseEasting = array();


		function GeoTrans($srctype = "katec", $dsttype = "geo")
		{
			$this->m_arScaleFactor["geo"] = 1;
			$this->m_arLonCenter["geo"] = 0.0;
			$this->m_arLatCenter["geo"] = 0.0;
			$this->m_arFalseNorthing["geo"] = 0.0;
			$this->m_arFalseEasting["geo"] = 0.0;

			$this->m_arScaleFactor["katec"] = 0.9999;
			$this->m_arLonCenter["katec"] = 2.23402144255274;
			$this->m_arLatCenter["katec"] = 0.663225115757845;
			$this->m_arFalseNorthing["katec"] = 600000.0;
			$this->m_arFalseEasting["katec"] = 400000.0;

			$this->srctype = $srctype;
			$this->dsttype = $dsttype;

			$temp = $this->m_arMinor / $this->m_arMajor;
			$this->m_Es = 1.0 - $temp * $temp;
			$this->m_Esp = $this->m_Es / (1.0 - $this->m_Es);

			if ($this->m_Es < 0.00001) {
				$this->m_Ind = 1.0;
			}
			else {
				$this->m_Ind = 0.0;
			}

			$this->src_m = $this->m_arMajor * $this->mlfn($this->e0fn($this->m_Es), $this->e1fn($this->m_Es), $this->e2fn($this->m_Es), $this->e3fn($this->m_Es), $this->m_arLatCenter[$srctype]);
			$this->dst_m = $this->m_arMajor * $this->mlfn($this->e0fn($this->m_Es), $this->e1fn($this->m_Es), $this->e2fn($this->m_Es), $this->e3fn($this->m_Es), $this->m_arLatCenter[$dsttype]);
		}

		function D2R($degree)
		{
			return $degree * M_PI / 180.0;
		}

		function R2D($radian)
		{
			return $radian * 180.0 / M_PI;
		}

		function e0fn($x)
		{
			return 1.0 - 0.25 * $x * (1.0 + $x / 16.0 * (3.0 + 1.25 * $x));
		}

		function e1fn($x)
		{
			return 0.375 * $x * (1.0 + 0.25 * $x * (1.0 + 0.46875 * $x));
		}

		function e2fn($x)
		{
			return 0.05859375 * $x * $x * (1.0 + 0.75 * $x);
		}

		function e3fn($x)
		{
			return $x * $x * $x * (35.0 / 3072.0);
		}

		function mlfn($e0, $e1, $e2, $e3, $phi)
		{
			return $e0 * $phi - $e1 * sin(2.0 * $phi) + $e2 * sin(4.0 * $phi) - $e3 * sin(6.0 * $phi);
		}

		function asinz($value)
		{
			if (abs($value) > 1.0) $value = ($value > 0 ? 1 : -1);
			return asin($value);
		}

		function conv($in_x, $in_y, &$out_x, &$out_y)
		{
			if ($this->srctype == "geo") {
				$inlon = $this->D2R($in_x);
				$inlat = $this->D2R($in_y);
			}
			else {
				$this->tm2geo($in_x, $in_y, $inlon, $inlat);
			}

			$outlon = $inlon;
			$outlat = $inlat;

			if ($this->dsttype == "geo") {
				$out_x = $this->R2D($outlon);
				$out_y = $this->R2D($outlat);
			}
			else {
				$this->geo2tm($outlon, $outlat, $out_x, $out_y);
				$out_x = round($out_x);
				$out_y = round($out_y);
			}
		}

		function geo2tm($lon, $lat, &$x, &$y)
		{
			$delta_lon = $lon - $this->m_arLonCenter[$this->dsttype];
			$sin_phi = sin($lat);
			$cos_phi = cos($lat);

			if ($this->m_Ind != 0) {
				$b = $cos_phi * sin($delta_lon);

				if ((abs(abs($b) - 1.0)) < $this->EPSLN) {
					echo ("geo2tm: 무한대 에러");
				}
			}
			else {
				$b = 0;
				$x = 0.5 * $this->m_arMajor * $this->m_arScaleFactor[$this->dsttype] * log((1.0 + $b) / (1.0 - $b));
				$con = acos($cos_phi * cos($delta_lon) / sqrt(1.0 - $b * $b));

				if ($lat < 0) {
					$con = $con * -1;
					$y = $this->m_arMajor * $this->m_arScaleFactor[$this->dsttype] * ($con - $this->m_arLatCenter[$this->dsttype]);
				}
			}

			$al = $cos_phi * $delta_lon;
			$als = $al * $al;
			$c = $this->m_Esp * $cos_phi * $cos_phi;
			$tq = tan($lat);
			$t = $tq * $tq;
			$con = 1.0 - $this->m_Es * $sin_phi * $sin_phi;
			$n = $this->m_arMajor / sqrt($con);
			$ml = $this->m_arMajor * $this->mlfn($this->e0fn($this->m_Es), $this->e1fn($this->m_Es), $this->e2fn($this->m_Es), $this->e3fn($this->m_Es), $lat);

			$x = $this->m_arScaleFactor[$this->dsttype] * $n * $al * (1.0 + $als / 6.0 * (1.0 - $t + $c + $als / 20.0 * (5.0 - 18.0 * $t + $t * $t + 72.0 * $c - 58.0 * $this->m_Esp))) + $this->m_arFalseEasting[$this->dsttype];
			$y = $this->m_arScaleFactor[$this->dsttype] * ($ml - $this->dst_m + $n * $tq * ($als * (0.5 + $als / 24.0 * (5.0 - $t + 9.0 * $c + 4.0 * $c * $c + $als / 30.0 * (61.0 - 58.0 * $t + $t * $t + 600.0 * $c - 330.0 * $this->m_Esp))))) + $this->m_arFalseNorthing[$this->dsttype];
		}

		function tm2geo($x, $y, &$lon, &$lat)
		{
			$max_iter = 6;

			if ($this->m_Ind != 0) {
				$f = exp($x / ($this->m_arMajor * $this->m_arScaleFactor[$this->srctype]));
				$g = 0.5 * ($f - 1.0 / $f);
				$temp = $this->m_arLatCenter[$this->srctype] + $y / ($this->m_arMajor * $this->m_arScaleFactor[$this->srctype]);
				$h = cos($temp);
				$con = sqrt((1.0 - $h * $h) / (1.0 + $g * $g));
				$lat = asinz($con);

				if ($temp < 0) $lat *= -1;

				if (($g == 0) && ($h == 0))
					$lon = $this->m_arLonCenter[$this->srctype];
				else
					$lon = atan($g / $h) + $this->m_arLonCenter[$this->srctype];
			}

			$x -= $this->m_arFalseEasting[$this->srctype];
			$y -= $this->m_arFalseNorthing[$this->srctype];

			$con = ($this->src_m + $y / $this->m_arScaleFactor[$this->srctype]) / $this->m_arMajor;
			$phi = $con;

			$i = 0;

			while (true) {
				$delta_Phi = (($con + $this->e1fn($this->m_Es) * sin(2.0 * $phi) - $this->e2fn($this->m_Es) * sin(4.0 * $phi) + $this->e3fn($this->m_Es) * sin(6.0 * $phi)) / $this->e0fn($this->m_Es)) - $phi;
				$phi = $phi + $delta_Phi;
				if (abs($delta_Phi) <= $this->EPSLN) break;

				if ($i >= $max_iter) 
					echo ("tm2geo: 무한대 에러");

				$i++;
			}

			if (abs($phi) < (M_PI / 2)) {
				$sin_phi = sin($phi);
				$cos_phi = cos($phi);
				$tan_phi = tan($phi);
				$c = $this->m_Esp * $cos_phi * $cos_phi;
				$cs = $c * $c;
				$t = $tan_phi * $tan_phi;
				$ts = $t * $t;
				$con = 1.0 - $this->m_Es * $sin_phi * $sin_phi;
				$n = $this->m_arMajor / sqrt($con);
				$r = $n * (1.0 - $this->m_Es) / $con;
				$d = $x / ($n * $this->m_arScaleFactor[$this->srctype]);
				$ds = $d * $d;
				$lat = $phi - ($n * $tan_phi * $ds / $r) * (0.5 - $ds / 24.0 * (5.0 + 3.0 * $t + 10.0 * $c - 4.0 * $cs - 9.0 * $this->m_Esp - $ds / 30.0 * (61.0 + 90.0 * $t + 298.0 * $c + 45.0 * $ts - 252.0 * $this->m_Esp - 3.0 * $cs)));
				$lon = $this->m_arLonCenter[$this->srctype] + ($d * (1.0 - $ds / 6.0 * (1.0 + 2.0 * $t + $c - $ds / 20.0 * (5.0 - 2.0 * $c + 28.0 * $t - 3.0 * $cs + 8.0 * $this->m_Esp + 24.0 * $ts))) / $cos_phi);
			}
			else {
				$lat = M_PI * 0.5 * sin($y);
				$lon = $this->m_arLonCenter[$this->srctype];
			}
		}

		function getDistancebyGeo($lon1, $lat1, $lon2, $lat2)
		{
			$lat1 = $this->D2R($lat1);
			$lon1 = $this->D2R($lon1);
			$lat2 = $this->D2R($lat2);
			$lon2 = $this->D2R($lon2);

			$longitude = $lon2 - $lon1;
			$latitude  = $lat2 - $lat1;

			$a = pow(sin($latitude / 2.0), 2) + cos($lat1) * cos($lat2) * pow(sin($longitude / 2.0), 2);
			return 6376.5 * 2.0 * atan2(sqrt($a), sqrt(1.0 - $a));
		}

		function getDistancebyKatec($x1, $y1, $x2, $y2)
		{
			$geo = new GeoTrans;
			$geo->conv($x1, $y1, $lon1, $lat1);
			$geo->conv($x2, $y2, $lon2, $lat2);

			return $this->getDistancebyGeo($lon1, $lat1, $lon2, $lat2);
		}

		function getTimebySec($distance)
		{
			return round(3600 * $distance / 4);
		}

		function getTimebyMin($distance)
		{
			return (int)ceil($this->getTimebySec($distance) / 60);
		}
	}
?>
