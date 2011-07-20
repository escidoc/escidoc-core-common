// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EscidocRunAsInternalUserToken.java

package de.escidoc.core.common.util.service;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class EscidocRunAsInternalUserToken extends AbstractAuthenticationToken
{

    public EscidocRunAsInternalUserToken(Authentication orginalAuthentication)
    {
        super(orginalAuthentication.getAuthorities());
        this.orginalAuthentication = orginalAuthentication;
        setDetails(orginalAuthentication.getDetails());
        setAuthenticated(true);
    }

    public Authentication getOrginalAuthentication()
    {
        return orginalAuthentication;
    }

    public Object getCredentials()
    {
        return orginalAuthentication.getCredentials();
    }

    public Object getPrincipal()
    {
        return orginalAuthentication.getPrincipal();
    }

    public Object getDetails()
    {
        return orginalAuthentication.getDetails();
    }

    public Collection<GrantedAuthority> getAuthorities()
    {
        return orginalAuthentication.getAuthorities();
    }

    public String getName()
    {
        return orginalAuthentication.getName();
    }

    private final Authentication orginalAuthentication;
}
