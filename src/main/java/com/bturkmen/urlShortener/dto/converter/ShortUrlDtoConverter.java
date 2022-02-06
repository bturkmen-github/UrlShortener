package com.bturkmen.urlShortener.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bturkmen.urlShortener.dto.ShortUrlDto;
import com.bturkmen.urlShortener.model.ShortUrl;

@Component
public class ShortUrlDtoConverter {

	public ShortUrlDto convertToDto(ShortUrl shortUrl) {
		
		return ShortUrlDto.builder()
				.id(shortUrl.getId())
				.url(shortUrl.getUrl())
				.code(shortUrl.getCode())
				.build();
	}
	
	public List<ShortUrlDto> convertToDto(List<ShortUrl> shortUrls) {
		return shortUrls.stream()
				.map(x->convertToDto(x))
				.collect(Collectors.toList());
		
	}
	
	
}
