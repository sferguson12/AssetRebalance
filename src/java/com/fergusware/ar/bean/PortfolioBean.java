package com.fergusware.ar.bean;

import com.fergusware.ar.model.Allocation;
import com.fergusware.ar.model.Asset;
import com.fergusware.ar.model.Portfolio;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Steve Ferguson <sfergus1@gmail.com>
 */
@ManagedBean(name = "portfolio")
@RequestScoped
public class PortfolioBean {
  private static final NumberFormat PCT_FORMAT = NumberFormat.getPercentInstance();
  private static final NumberFormat PRICE_FORMAT = NumberFormat.getCurrencyInstance();
  private static final NumberFormat SHARES_FORMAT = NumberFormat.getNumberInstance();

  static {
    PCT_FORMAT.setMinimumFractionDigits(3);
    PCT_FORMAT.setMaximumFractionDigits(3);
    
    PRICE_FORMAT.setMinimumFractionDigits(4);
    PRICE_FORMAT.setMaximumFractionDigits(4);

    SHARES_FORMAT.setMinimumFractionDigits(4);
    SHARES_FORMAT.setMaximumFractionDigits(4);
  }

//<editor-fold defaultstate="collapsed" desc="Properties">
  private Portfolio portfolio;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Constructors">
  public PortfolioBean() { }

  public PortfolioBean(Portfolio portfolio) {
    this.portfolio = portfolio;
  }
//</editor-fold>
  
//<editor-fold defaultstate="collapsed" desc="Accessors">
  public void setPortfolio(Portfolio value) { portfolio = value; }
  public Portfolio getPortfolio() { return portfolio; }
  //</editor-fold>

  public List<Asset> getAssets() { return portfolio.getAssets(); }

  public Asset.AssetClass[] getAssetClasses() {
    return Asset.AssetClass.values();
  }

  public Asset.AssetStyle[] getAssetStyles() {
    return Asset.AssetStyle.values();
  }

  public Asset.Location[] getLocations() {
    return Asset.Location.values();
  }

  public Asset.MarketCap[] getMarketCaps() {
    return Asset.MarketCap.values();
  }

  public String getAssetPrice(Asset asset) {
    return PRICE_FORMAT.format(asset.getSharePrice());
  }

  public String getAssetShares(Asset asset) {
    return SHARES_FORMAT.format(asset.getShares());
  }

  public String getCurrentAllocation(Asset asset) {
    BigDecimal value = portfolio.getCurrentAllocation().getAssetMap().get(asset);
    return PCT_FORMAT.format(value);
  }

  public String getTargetAllocation(Asset asset) {
    BigDecimal value = portfolio.getTargetAllocation().getAssetMap().get(asset);
    return PCT_FORMAT.format(value);
  }

  @PostConstruct
  public void init() {
    // Initialize with bogus data for now
    portfolio = new Portfolio();

    List<Asset> assets = Arrays.asList(
      new Asset(Asset.AssetClass.Stock, new BigDecimal("55455.636"), new BigDecimal("35.7913")),
      new Asset(Asset.AssetClass.Bond, new BigDecimal("4242.4242"), new BigDecimal("123.4567")),
      new Asset(Asset.AssetClass.Cash, new BigDecimal("25000"), new BigDecimal("1")),
      new Asset(Asset.AssetClass.Property, new BigDecimal("1"), new BigDecimal("180000"))
    );

    assets.get(0).setName("Google");
    assets.get(1).setName("Vanguard Mid-Cap Bond Fund");
    assets.get(0).setSymbol("GOOG");
    assets.get(1).setSymbol("VBLTX");
    assets.get(0).setAssetStyle(Asset.AssetStyle.Growth);
    assets.get(1).setAssetStyle(Asset.AssetStyle.Value);
    assets.get(1).setMarketCap(Asset.MarketCap.Mid);

    Map<Asset, BigDecimal> targetMap = new HashMap<>();
    targetMap.put(assets.get(0), new BigDecimal("0.74"));
    targetMap.put(assets.get(1), new BigDecimal("0.20"));
    targetMap.put(assets.get(2), new BigDecimal("0.01"));
    targetMap.put(assets.get(3), new BigDecimal("0.05"));
    Allocation target = new Allocation(targetMap);

    assets.stream().forEach((asset) -> {
      portfolio.addAsset(asset);
    });
    portfolio.setTargetAllocation(target);
  }
}
