package com.springinaction.spitter.mvc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.mvc.exception.ImageUploadException;
import com.springinaction.spitter.service.SpitterService;

@Controller
@RequestMapping("/spitters")
public class SpitterController {

	private final SpitterService spitterService;
	
	@Inject
	public SpitterController(SpitterService spitterService) {
		this.spitterService = spitterService;
	}
	
	@RequestMapping(value="/spittles", method=RequestMethod.GET)
	public String listSpittlesForSpitter(@RequestParam("spitter") String username, Model model) {
		Spitter spitter = spitterService.getSpitter(username);
		model.addAttribute(spitter);
		model.addAttribute(spitterService.getSpittlesForSpitter(username));
		return "spittles/list";
	}
	
	@RequestMapping(method=RequestMethod.GET, params="new")
	public String createSpitterProfile(Model model) {
		model.addAttribute(new Spitter());
		return "spitters/edit";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addSpitterFromForm(@Valid Spitter spitter, BindingResult bindingResult,
			@RequestParam(value="image", required=false) MultipartFile image) {
		if(bindingResult.hasErrors()) {
			return "spitters/edit";
		}
		spitterService.saveSpitter(spitter);
		
		try {
			if(!image.isEmpty()) {
				validateImage(image);
				saveImage(spitter.getId() + ".jpg", image);
			}
		} 
		catch(ImageUploadException e) {
			bindingResult.reject(e.getMessage());
			return "spitters/edit";
		}
		
		return "redirect:/spitters/" + spitter.getUsername();
	}
	
	private void saveImage(String filename, MultipartFile image) throws ImageUploadException {
		try {
			/*
			String webRootPath = null;
			File file = new File(webRootPath + "/resources/" + filename);
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			*/
			String s3AccessKey = null, s3SecretKey = null;
			AWSCredentials awsCredentials = new AWSCredentials(s3AccessKey, s3SecretKey);
			S3Service s3 = new RestS3Service(awsCredentials);
			
			S3Bucket imageBucket = s3.getBucket("spitterImages");
			S3Object imageObject = new S3Object(filename);
			
			imageObject.setDataInputStream(new ByteArrayInputStream(image.getBytes()));
			imageObject.setContentType("image/jpeg");
			imageObject.setContentLength(image.getBytes().length);
			
			AccessControlList acl = new AccessControlList();
			acl.setOwner(imageBucket.getOwner());
			acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
			imageObject.setAcl(acl);
			
			s3.putObjectAcl(imageBucket, imageObject);
		}
		catch(Exception e) {
			throw new ImageUploadException("Unable to save image", e);
		}
	}

	private void validateImage(MultipartFile image) {
		if(!image.getContentType().equals("image/jpeg")) {
			throw new ImageUploadException("Only JPG images accepted");
		}
		
	}

	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		model.addAttribute(spitterService.getSpitter(username));
		return "spitters/view";
	}
}
