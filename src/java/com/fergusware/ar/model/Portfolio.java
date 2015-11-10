package com.fergusware.ar.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stf
 */
public class Portfolio {
//<editor-fold defaultstate="collapsed" desc="Properties">
    List<Asset> assets;
    Allocation target;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Constructors">
    public Portfolio() {
        assets = new ArrayList<>();
    }
    
    public Portfolio(List<Asset> assets) {
        this.assets = assets;
    }

    public Portfolio(List<Asset> assets, Allocation target) {
        this.assets = assets;
        this.target = target;

        for (int i = 0; i < assets.size(); i++) {
            assets.get(i).setPortfolioId(i + 1);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Accessors">
    public List<Asset> getAssets() { return assets; }
    
    public Allocation getTargetAllocation() { return target; }
    public void setTargetAllocation(Allocation value) { target = value; }
//</editor-fold>
    
    public void addAsset(Asset asset) {
        asset.setPortfolioId(assets.size() + 1);
        assets.add(asset);
    }

    public Asset getAssetById(int id) {
        return assets.stream().filter(x -> x.getPortfolioId() == id).findFirst().get();
    }

    public BigDecimal getTotalValue() {
        BigDecimal total = BigDecimal.ZERO;

        for (Asset asset : assets) {
            total = total.add(asset.getValue());
        }

        return total;
    }

    public Allocation getCurrentAllocation() {
        return new Allocation(assets, getTotalValue());
    }

    public Portfolio getTargetPortfolio() {
        Portfolio portfolio = new Portfolio();
        BigDecimal total = getTotalValue();

        assets.stream().map((asset) -> {
            Asset targetAsset = new Asset(asset);
            BigDecimal targetValue = total.multiply(target.getAsset(asset));
            BigDecimal targetShares = targetValue.divide(targetAsset.getSharePrice(), 6, BigDecimal.ROUND_HALF_EVEN);
            targetAsset.setShares(targetShares);
            return targetAsset;
        }).forEach((targetAsset) -> {
            portfolio.addAsset(targetAsset);
        });

        return portfolio;
    }
}
