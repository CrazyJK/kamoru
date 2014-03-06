package jk.kamoru.test.torrent;

import java.io.File;
import java.io.IOException;

import com.turn.ttorrent.common.Torrent;

public class TorrentTest {

	public static void main(String[] args) throws IOException {

		String file = "/Users/kamoru/Downloads/insbitz.com-2013122838820131228523201312289_2020.torrent";
		File torrentFile = new File(file);

		Torrent torrent = Torrent.load(torrentFile);

		System.out.println("toString - " + torrent);

		for (String name : torrent.getFilenames()) {
			System.out.println("filename : " + name);
		}

		System.out.println("hash - " + torrent.getHexInfoHash());

		System.out.println("encode - " + torrent.getEncoded());

		System.out.println("name - " + torrent.getName());

		System.out.println("size - " + torrent.getSize());

		System.out.println("multifile - " + torrent.isMultifile());

	}

}
