package com.bturkmen.urlShortener.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bturkmen.urlShortener.exception.CodeAlreadyExists;
import com.bturkmen.urlShortener.exception.ShortUrlNotFoundException;
import com.bturkmen.urlShortener.model.ShortUrl;
import com.bturkmen.urlShortener.repository.ShortUrlRepository;
import com.bturkmen.urlShortener.util.RandomStringGenerator;

@Service
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	private final RandomStringGenerator stringGenerator;
	
	public ShortUrlService(ShortUrlRepository shortUrlRepository,
						RandomStringGenerator stringGenerator) {
		this.shortUrlRepository = shortUrlRepository;
		this.stringGenerator = stringGenerator;
	}

	public List<ShortUrl> getAllShortUrl() {
		return shortUrlRepository.findAll();
	}

	public ShortUrl getUrlByCode(String code) {
		
		return shortUrlRepository.findAllByCode(code).orElseThrow(
				()->new ShortUrlNotFoundException("Url Bulunamadı")
				);
	}

	public ShortUrl create(ShortUrl shortUrl) {
		
		if(shortUrl.getCode() == null || shortUrl.getCode().isEmpty()) {
			shortUrl.setCode(generateCode());
		}else if(shortUrlRepository.findAllByCode(shortUrl.getCode()).isPresent()) {
			throw new CodeAlreadyExists("Bu Code Daha Önce Kullanıldı");
		}	
		
		shortUrl.setCode(shortUrl.getCode().toUpperCase());
		
		return shortUrlRepository.save(shortUrl);
	}
	
	private String generateCode() {
		
		String code;
		
		do {
			code = stringGenerator.generateRandomString();
		}while(shortUrlRepository.findAllByCode(code).isPresent());
		
		return code;
	}
	
	
	
}
