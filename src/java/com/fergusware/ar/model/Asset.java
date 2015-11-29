package com.fergusware.ar.model;

import java.math.BigDecimal;

/**
 *
 * @author Steve Ferguson <sfergus1@gmail.com>
 */
public class Asset {
  public enum AssetClass { Bond, Cash, Property, Stock };
  public enum AssetStyle { None, Blend, Growth, Value };
  public enum Location { None, Domestic, Emerging, Foreign };
  public enum MarketCap { None, Large, Micro, Mid, Small };
    
//<editor-fold defaultstate="collapsed" desc="Properties">
  // Primary, required attributes
  private AssetClass assetClass;
  private BigDecimal shares;
  private BigDecimal sharePrice;

  // Optional attributes
  private String name;
  private String symbol;
  private AssetStyle style;
  private Location location;
  private MarketCap marketCap;
  private int portfolioId;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Constructors">
  public Asset() { }

  public Asset(AssetClass assetClass, BigDecimal shares, BigDecimal price) {
    this.assetClass = assetClass;
    this.shares = shares;
    this.sharePrice = price;
  }

  public Asset(Asset copy) {
    this.name = copy.name;
    this.assetClass = copy.assetClass;
    this.shares = copy.shares;
    this.sharePrice = copy.sharePrice;
    this.symbol = copy.symbol;
    this.style = copy.style;
    this.location = copy.location;
    this.marketCap = copy.marketCap;
    this.portfolioId = copy.portfolioId;
  }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Accessors">
  public String getName() { return name; }
  public void setName(String value) { name = value; }

  public AssetClass getAssetClass() { return assetClass; }
  public void setAssetClass(AssetClass value) { assetClass = value; }

  public BigDecimal getShares() { return shares; }
  public void setShares(BigDecimal value) { shares = value; }

  public BigDecimal getSharePrice() { return sharePrice; }
  public void setSharePrice(BigDecimal value) { sharePrice = value; }

  public String getSymbol() { return symbol; }
  public void setSymbol(String value) { symbol = value; }

  public AssetStyle getAssetStyle() { return style; }
  public void setAssetStyle(AssetStyle value) { style = value; }

  public Location getLocation() { return location; }
  public void setLocation(Location value) { location = value; }

  public MarketCap getMarketCap() { return marketCap; }
  public void setMarketCap(MarketCap value) { marketCap = value; }

  public int getPortfolioId() { return portfolioId; }
  public void setPortfolioId(int value) { portfolioId = value; }
//</editor-fold>

  public BigDecimal getValue() {
    return shares.multiply(sharePrice);
  }
}

