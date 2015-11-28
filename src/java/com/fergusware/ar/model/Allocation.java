package com.fergusware.ar.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Steve Ferguson <sfergus1@gmail.com>
 */
public class Allocation {
//<editor-fold defaultstate="collapsed" desc="Properties">
  Map<Asset, BigDecimal> assetMap;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Constructors">
  public Allocation() { }

  public Allocation(Map<Asset, BigDecimal> assetMap) {
    this.assetMap = assetMap;
  }

  public Allocation(List<Asset> assets, BigDecimal total) {
    assetMap = new HashMap<>();
    derive(assets, total);
  }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Accessors">
  public Map<Asset, BigDecimal> getAssetMap() { return assetMap; }
  public void setAssetMap(Map<Asset, BigDecimal> value) { assetMap = value; }
//</editor-fold>

  public BigDecimal getAsset(Asset asset) {
    return assetMap.get(asset);
  }

  private void derive(List<Asset> assets, BigDecimal total) {
    assets.stream().forEach((asset) -> {
      BigDecimal part = asset.getValue().divide(total, 6, BigDecimal.ROUND_HALF_EVEN);
      assetMap.put(asset, part);
    });
  }
}
