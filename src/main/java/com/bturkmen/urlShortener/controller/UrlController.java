package com.bturkmen.urlShortener.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bturkmen.urlShortener.dto.ShortUrlDto;
import com.bturkmen.urlShortener.dto.converter.ShortUrlDtoConverter;
import com.bturkmen.urlShortener.model.ShortUrl;
import com.bturkmen.urlShortener.request.ShortUrlRequest;
import com.bturkmen.urlShortener.request.converter.ShortUrlRequestConverter;
import com.bturkmen.urlShortener.service.ShortUrlService;

@RestController
public class UrlController {

	private final ShortUrlDtoConverter shortUrlDtoConverter;
	private final ShortUrlRequestConverter shortUrlRequestConverter;
	private final ShortUrlService service;
	
	public UrlController(ShortUrlDtoConverter shortUrlDtoConverter,
							ShortUrlRequestConverter shortUrlRequestConverter,
							ShortUrlService service) {
		this.shortUrlDtoConverter = shortUrlDtoConverter;
		this.shortUrlRequestConverter = shortUrlRequestConverter;
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<ShortUrlDto>> getAllUrls(){
		return new ResponseEntity<List<ShortUrlDto>>(
				shortUrlDtoConverter.convertToDto(service.getAllShortUrl()),
				HttpStatus.OK
				);
	}
	
	@GetMapping("/show/{code}")
	public ResponseEntity<ShortUrlDto> getUrlByCode(@Valid @NotEmpty @PathVariable String code){
		return new ResponseEntity<ShortUrlDto>(
				shortUrlDtoConverter.convertToDto(service.getUrlByCode(code)),
				HttpStatus.OK
				);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<ShortUrlDto> redirect(@Valid @NotEmpty @PathVariable String code) throws URISyntaxException{
		
		ShortUrl shortUrl = service.getUrlByCode(code);
		
		URI uri = new URI(shortUrl.getUrl());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);
		
		
		return new ResponseEntity<>(httpHeaders,HttpStatus.SEE_OTHER);
	}
	
	
	@PostMapping
	public ResponseEntity<ShortUrlDto> create(@Valid @RequestBody ShortUrlRequest request){
		ShortUrl shortUrl =  shortUrlRequestConverter.convertToEntity(request);
		return new ResponseEntity<ShortUrlDto>(
				shortUrlDtoConverter.convertToDto(service.create(shortUrl)),
				HttpStatus.CREATED);
				
	}
	
	
}
