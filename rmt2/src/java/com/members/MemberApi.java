package com.members;


/**
 * An interface that provides the method contracts for implementing the Members API.
 * 
 * @author RTerrell
 *
 */
public interface MemberApi {

    void close();

    /**
     * Fetch member by unique id.
     * 
     * @param memberId 
     *          member's internal unique id
     * @return Object
     *          an arbitrary object when found or null when not found
     * @throws MemberException
     */
    Object findById(int memberId) throws MemberException;

    /**
     * Fetch member by email address
     * 
     * @param emailAddr 
     *          the email address of a member.
     * @return Object
     *          an arbitrary object when found or null when not found.
     * @throws MemberException
     */
    Object findByEmail(String emailAddr) throws MemberException;

}
