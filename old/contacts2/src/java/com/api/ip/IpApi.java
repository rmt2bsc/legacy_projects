package com.api.ip;

import com.bean.IpLocation;

/**
 * @author appdev
 *
 */
public interface IpApi {
    
    IpLocation getIpDetails(String ip) throws IpException;
    
    IpLocation getIpDetails(long ip) throws IpException;

}
