// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EscidocRequestDetail.java

package de.escidoc.core.common.util.service;

import java.io.Serializable;

public class EscidocRequestDetail
    implements Serializable
{

    public EscidocRequestDetail()
    {
        restAccess = false;
        restrictedPermissionCode = 0;
    }

    public void setRestAccess(boolean restAccess)
    {
        this.restAccess = restAccess;
    }

    public boolean isRestAccess()
    {
        return restAccess;
    }

    public void setRestrictedPermissions(int restrictedPermissions)
    {
        restrictedPermissionCode = restrictedPermissions;
    }

    public int getRestrictedPermissions()
    {
        return restrictedPermissionCode;
    }

    private static final long serialVersionUID = 0xbdaa397d5f292902L;
    private boolean restAccess;
    private int restrictedPermissionCode;
}
