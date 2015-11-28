package com.fergusware.ar.model;

import com.fergusware.ar.model.Asset.AssetClass;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steve Ferguson <sfergus1@gmail.com>
 */
public class PortfolioTest {
  private List<Asset> Assets;
  private Map<Asset, BigDecimal> CurrentMap;
  private Map<Asset, BigDecimal> TargetMap;
  private Allocation Current;
  private Allocation Target;

  public PortfolioTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
    Assets = Arrays.asList(
      new Asset(AssetClass.Stock, new BigDecimal("55455.636"), new BigDecimal("35.7913")),
      new Asset(AssetClass.Bond, new BigDecimal("4242.4242"), new BigDecimal("123.4567")),
      new Asset(AssetClass.Cash, new BigDecimal("25000"), new BigDecimal("1")),
      new Asset(AssetClass.Property, new BigDecimal("1"), new BigDecimal("180000"))
    );

    CurrentMap = new HashMap<>();
    CurrentMap.put(Assets.get(0), new BigDecimal("0.731442"));
    CurrentMap.put(Assets.get(1), new BigDecimal("0.193012"));
    CurrentMap.put(Assets.get(2), new BigDecimal("0.009213"));
    CurrentMap.put(Assets.get(3), new BigDecimal("0.066333"));
    Current = new Allocation(CurrentMap);

    TargetMap = new HashMap<>();
    TargetMap.put(Assets.get(0), new BigDecimal("0.74"));
    TargetMap.put(Assets.get(1), new BigDecimal("0.20"));
    TargetMap.put(Assets.get(2), new BigDecimal("0.01"));
    TargetMap.put(Assets.get(3), new BigDecimal("0.05"));
    Target = new Allocation(TargetMap);
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of getAssets method, of class Portfolio.
   */
  @Test
  public void testGetAssets() {
    System.out.println("getAssets");

    Portfolio empty = new Portfolio();
    assertEquals(new ArrayList<>(), empty.getAssets());
  }

  /**
   * Test of getTargetAllocation method, of class Portfolio.
   */
  @Test
  public void testGetTargetAllocation() {
    System.out.println("getTargetAllocation");

    Portfolio empty = new Portfolio();
    assertEquals(null, empty.getTargetAllocation());
  }

  /**
   * Test of setTargetAllocation method, of class Portfolio.
   */
  @Test
  public void testSetTargetAllocation() {
    System.out.println("setTargetAllocation");

    Portfolio instance = new Portfolio();
    instance.setTargetAllocation(Target);
    assertEquals(Target, instance.getTargetAllocation());
  }

  /**
   * Test of addAsset method, of class Portfolio.
   */
  @Test
  public void testAddAsset() {
    System.out.println("addAsset");

    Portfolio instance = new Portfolio();
    instance.addAsset(Assets.get(0));
    assertEquals(Arrays.asList(Assets.get(0)), instance.getAssets());
    instance.addAsset(Assets.get(1));
    assertEquals(Arrays.asList(Assets.get(0), Assets.get(1)), instance.getAssets());
  }

  /**
   * Test of getTotalValue method, of class Portfolio.
   */
  @Test
  public void testGetTotalValue() {
    System.out.println("getTotalValue");

    Portfolio instance = new Portfolio(Assets);
    assertEquals(new BigDecimal("2713584.99649894"), instance.getTotalValue());
  }

  /**
   * Test of getCurrentAllocation method, of class Portfolio.
   */
  @Test
  public void testGetCurrentAllocation() {
    System.out.println("getCurrentAllocation");

    Portfolio instance = new Portfolio(Assets);
    Allocation current = instance.getCurrentAllocation();
    Assets.stream().forEach((asset) -> {
      assertEquals(Current.getAsset(asset), current.getAsset(asset));
    });
  }

  /**
   * Test of getTargetAssets method, of class Portfolio.
   */
  @Test
  public void testGetTargetAssets() {
    System.out.println("getTargetAssets");

    Portfolio instance = new Portfolio(Assets, Target);
    Portfolio rebalanced = instance.getTargetPortfolio();

    Allocation currentAllocation = instance.getCurrentAllocation();
    Allocation proposedAllocation = rebalanced.getCurrentAllocation();

    BigDecimal tolerance = new BigDecimal("0.01"); // Require within 1 percent
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    NumberFormat percent = NumberFormat.getPercentInstance();
    percent.setMinimumFractionDigits(2);

    instance.getAssets().stream().map((currentAsset) -> {
      Asset proposedAsset = rebalanced.getAssetById(currentAsset.getPortfolioId());
      System.out.println(String.format("Asset #%d", currentAsset.getPortfolioId()));
      System.out.println(String.format("Alloc\tBefore: %s, After: %s, Change: %s",
              percent.format(currentAllocation.getAsset(currentAsset)),
              percent.format(proposedAllocation.getAsset(proposedAsset)),
              percent.format(proposedAllocation.getAsset(proposedAsset).subtract(currentAllocation.getAsset(currentAsset)))));
      System.out.println(String.format("Shares\tBefore: %s, After: %s, Change: %s",
              currentAsset.getShares().setScale(4, BigDecimal.ROUND_HALF_EVEN),
              proposedAsset.getShares().setScale(4, BigDecimal.ROUND_HALF_EVEN),
              proposedAsset.getShares().subtract(currentAsset.getShares()).setScale(4, BigDecimal.ROUND_HALF_EVEN)));
      System.out.println(String.format("Value\tBefore: %s, After: %s, Change: %s",
              currency.format(currentAsset.getValue()),
              currency.format(proposedAsset.getValue()),
              currency.format(proposedAsset.getValue().subtract(currentAsset.getValue()))));
      BigDecimal delta = Target.getAsset(currentAsset).subtract(proposedAllocation.getAsset(proposedAsset));
      return delta;
    }).forEach((delta) -> {
      assertTrue(tolerance.compareTo(delta.abs()) > 0);
    });
  }
}
