package com.deongeon.ai.sourcing.service;

import java.util.List;

public interface CrawlerService {
    List<String> search(String keyword);
    void crawlDetail(String url);
}
