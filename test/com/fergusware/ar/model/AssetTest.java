package com.fergusware.ar.model;

import com.fergusware.ar.model.Asset.AssetClass;
import java.math.BigDecimal;
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
public class AssetTest {
  private static final BigDecimal Shares = new BigDecimal("123.4567");
  private static final BigDecimal SharePrice = new BigDecimal("42.4242");
  private static final BigDecimal Value = Shares.multiply(SharePrice);

  public AssetTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of getType method, of class Asset.
   */
  @Test
  public void testGetType() {
    System.out.println("getAssetClass");

    Asset empty = new Asset();
    assertNull(empty.getAssetClass());

    Asset instance = new Asset(AssetClass.Stock, Shares, SharePrice);
    assertEquals(AssetClass.Stock, instance.getAssetClass());
  }

  /**
   * Test of setType method, of class Asset.
   */
  @Test
  public void testSetType() {
    System.out.println("setType");

    Asset instance = new Asset();
    instance.setAssetClass(AssetClass.Bond);
    assertEquals(AssetClass.Bond, instance.getAssetClass());
 }

  /**
   * Test of getShares method, of class Asset.
   */
  @Test
  public void testGetShares() {
    System.out.println("getShares");

    Asset empty = new Asset();
    assertNull(empty.getShares());

    Asset instance = new Asset(AssetClass.Stock, Shares, SharePrice);
    assertEquals(Shares, instance.getShares());
  }

  /**
   * Test of setShares method, of class Asset.
   */
  @Test
  public void testSetShares() {
    System.out.println("setShares");

    Asset instance = new Asset();
    instance.setShares(Shares);
    assertEquals(Shares, instance.getShares());
  }

  /**
   * Test of getSharePrice method, of class Asset.
   */
  @Test
  public void testGetSharePrice() {
    System.out.println("getSharePrice");

    Asset empty = new Asset();
    assertNull(empty.getSharePrice());

    Asset instance = new Asset(AssetClass.Stock, Shares, SharePrice);
    assertEquals(SharePrice, instance.getSharePrice());
  }

  /**
   * Test of setSharePrice method, of class Asset.
   */
  @Test
  public void testSetSharePrice() {
    System.out.println("setSharePrice");

    Asset instance = new Asset();
    instance.setSharePrice(SharePrice);
    assertEquals(SharePrice, instance.getSharePrice());
  }

  /**
   * Test of getValue method, of class Asset.
   */
  @Test
  public void testGetValue() {
    System.out.println("getValue");

    Asset instance = new Asset(AssetClass.Stock, Shares, SharePrice);
    assertEquals(Value, instance.getValue());
  }

  /**
   * Test of getValue method, of class Asset.
   */
  @Test(expected = NullPointerException.class)
  public void testNullGetValue() {
    System.out.println("getValue - null");

    Asset instance = new Asset();
    instance.getValue();
  }
}
