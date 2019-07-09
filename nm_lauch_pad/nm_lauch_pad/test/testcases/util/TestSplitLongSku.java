/**
 * 
 */
package testcases.util;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.nv.util.GeneralUtil;


/**
 * @author rterrell
 *
 */
public class TestSplitLongSku {

    @Test
    public void splitShortSku() {
	Map<String, Integer> map = GeneralUtil.splitLongSku("12345678");
	Assert.assertEquals(0, map.get(GeneralUtil.SKU_KEY_UPPER).intValue());
	Assert.assertEquals(12345678, map.get(GeneralUtil.SKU_KEY_LOWER).intValue());
    }
    
    @Test
    public void splitLongSku() {
	Map<String, Integer> map = GeneralUtil.splitLongSku("123456788383838");
	Assert.assertEquals(1234567, map.get(GeneralUtil.SKU_KEY_UPPER).intValue());
	Assert.assertEquals(88383838, map.get(GeneralUtil.SKU_KEY_LOWER).intValue());
	
	map = GeneralUtil.splitLongSku("123456788338637261");
	Assert.assertEquals(1234567883, map.get(GeneralUtil.SKU_KEY_UPPER).intValue());
	Assert.assertEquals(38637261, map.get(GeneralUtil.SKU_KEY_LOWER).intValue());
	
    }
}


