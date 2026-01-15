package com.deongeon.ai.sourcing.dto;

public class AddSourceRequest {

    private String url;
    private String name;
    private Double costKrw;
    private Double costCny;
    private Double weightKg;

    public String getUrl() { return url; }
    public String getName() { return name; }
    public Double getCostKrw() { return costKrw; }
    public Double getCostCny() { return costCny; }
    public Double getWeightKg() { return weightKg; }
}
