package com.deongeon.ai.sourcing.service.impl;

import com.deongeon.ai.sourcing.service.CrawlerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleCrawlerService implements CrawlerService {

    @Override
    public List<String> search(String keyword) {
        return List.of(
            "https://www.taobao.com/item/123",
            "https://www.1688.com/item/456"
        );
    }

    @Override
    public void crawlDetail(String url) {
        System.out.println("크롤링 중: " + url);
    }
}
