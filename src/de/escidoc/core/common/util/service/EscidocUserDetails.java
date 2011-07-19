// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EscidocUserDetails.java

package de.escidoc.core.common.util.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EscidocUserDetails
    implements UserDetails
{

    public EscidocUserDetails()
    {
        id = null;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public void sign(String key)
    {
        if(key == null)
            return;
        else
            return;
    }

    public Collection<GrantedAuthority> getAuthorities()
    {
        return null;
    }

    public String getPassword()
    {
        return null;
    }

    public String getUsername()
    {
        return getRealName();
    }

    public boolean isAccountNonExpired()
    {
        return false;
    }

    public boolean isAccountNonLocked()
    {
        return false;
    }

    public boolean isCredentialsNonExpired()
    {
        return false;
    }

    public boolean isEnabled()
    {
        return false;
    }

    private static final long serialVersionUID = 1L;
    private String id;
    private String realName;
    public static final String ESCIDOC_USER_USERNAME = "eSciDocUser";
    public static final String SEARCH_USERNAME = "search";
    public static final String SEARCH_PASSWORD = "password";
}
