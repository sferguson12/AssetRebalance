package com.fergusware.ar.model;

import java.math.BigDecimal;
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
 * @author stf
 */
public class AllocationTest {
    private List<Asset> Assets;
    private Map<Asset, BigDecimal> AssetMap;
    
    public AllocationTest() {
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
            new Asset(Asset.AssetClass.Stock, new BigDecimal("55455.636"), new BigDecimal("35.7913")),
            new Asset(Asset.AssetClass.Bond, new BigDecimal("4242.4242"), new BigDecimal("123.4567")),
            new Asset(Asset.AssetClass.Cash, new BigDecimal("25000"), new BigDecimal("1")),
            new Asset(Asset.AssetClass.Property, new BigDecimal("1"), new BigDecimal("180000"))
        );

        AssetMap = new HashMap<>();
        AssetMap.put(Assets.get(0), new BigDecimal("0.50"));
        AssetMap.put(Assets.get(1), new BigDecimal("0.35"));
        AssetMap.put(Assets.get(2), new BigDecimal("0.15"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAssetMap method, of class Allocation.
     */
    @Test
    public void testGetAssetMap() {
        System.out.println("getAssetMap");

        Allocation empty = new Allocation();
        assertNull(empty.getAssetMap());

        Allocation instance = new Allocation(AssetMap);
        assertEquals(AssetMap, instance.getAssetMap());
    }

    /**
     * Test of setAssetMap method, of class Allocation.
     */
    @Test
    public void testSetAssetMap() {
        System.out.println("setAssetMap");

        Allocation instance = new Allocation();
        instance.setAssetMap(AssetMap);
        assertEquals(AssetMap, instance.getAssetMap());
    }

    /**
     * Test of getAsset method, of class Allocation.
     */
    @Test
    public void testGetAsset() {
        System.out.println("getAsset");

        Allocation instance = new Allocation(AssetMap);
        assertEquals(AssetMap.get(Assets.get(0)), instance.getAsset(Assets.get(0)));
    }
}
