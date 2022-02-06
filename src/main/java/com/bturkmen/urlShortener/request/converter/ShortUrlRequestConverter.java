package com.bturkmen.urlShortener.request.converter;

import org.springframework.stereotype.Component;

import com.bturkmen.urlShortener.model.ShortUrl;
import com.bturkmen.urlShortener.request.ShortUrlRequest;

@Component
public class ShortUrlRequestConverter {

	public ShortUrl convertToEntity(ShortUrlRequest request) {
		return ShortUrl.builder()
				.url(request.getUrl())
				.code(request.getCode())
				.build();
	}
}
