// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserContext.java

package de.escidoc.core.common.util.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.logger.AppLogger;

// Referenced classes of package de.escidoc.core.common.util.service:
//            EscidocUserDetails, EscidocRequestDetail, EscidocRunAsInternalUserToken

public final class UserContext
{

    private UserContext()
    {
    }

    public static void setUserContext(String handle)
        throws MissingMethodParameterException, WebserverSystemException
    {
        String hd = handle;
        if(hd == null)
            hd = "";
        SecurityContextHolder.clearContext();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new EscidocUserDetails(), hd);
        authentication.setAuthenticated(false);
        authentication.setDetails(new EscidocRequestDetail());
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    public static void setUserContext(SecurityContext context)
        throws MissingMethodParameterException, WebserverSystemException
    {
        if(context == null)
            throw new MissingMethodParameterException("No security context provided");
        SecurityContextHolder.clearContext();
        SecurityContextHolder.setContext(context);
        if(LOG.isDebugEnabled())
            LOG.debug((new StringBuilder()).append("Stored provided security context").append(getSecurityContext()).toString());
    }

    public static boolean runAsInternalUser()
        throws WebserverSystemException
    {
        Authentication authentication = getSecurityContext().getAuthentication();
        if(!(authentication instanceof EscidocRunAsInternalUserToken))
        {
            getSecurityContext().setAuthentication(new EscidocRunAsInternalUserToken(authentication));
            return true;
        } else
        {
            return false;
        }
    }

    public static boolean runAsExternalUser()
        throws WebserverSystemException
    {
        Authentication authentication = getSecurityContext().getAuthentication();
        if(authentication instanceof EscidocRunAsInternalUserToken)
        {
            getSecurityContext().setAuthentication(((EscidocRunAsInternalUserToken)authentication).getOrginalAuthentication());
            return true;
        } else
        {
            return false;
        }
    }

    public static void setId(String id)
        throws WebserverSystemException
    {
        getPrincipal().setId(id);
    }

    public static void setRealName(String realName)
        throws WebserverSystemException
    {
        if(realName == null)
        {
            return;
        } else
        {
            getPrincipal().setRealName(realName);
            return;
        }
    }

    public static void setRestAccess(boolean restUser)
        throws WebserverSystemException
    {
        getRequestDetails().setRestAccess(restUser);
    }

    public static SecurityContext getSecurityContext()
        throws WebserverSystemException
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext == null)
            throw new WebserverSystemException("UserContext is not initialized");
        else
            return securityContext;
    }

    private static Authentication getAuthentication()
        throws WebserverSystemException
    {
        Authentication authenticationToken = getSecurityContext().getAuthentication();
        if(authenticationToken == null)
            throw new WebserverSystemException("UserContext is not initialized");
        else
            return authenticationToken;
    }

    private static EscidocUserDetails getPrincipal()
        throws WebserverSystemException
    {
        return (EscidocUserDetails)getAuthentication().getPrincipal();
    }

    public static void setPrincipal(EscidocUserDetails principal)
        throws WebserverSystemException
    {
        Authentication oldAuthentication = getAuthentication();
        boolean isInternaluser = isInternalUser();
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(principal, oldAuthentication.getCredentials());
        newAuthentication.setDetails(oldAuthentication.getDetails());
        getSecurityContext().setAuthentication(newAuthentication);
        if(isInternaluser)
            runAsInternalUser();
    }

    public static String getId()
        throws WebserverSystemException
    {
        return getPrincipal().getId();
    }

    public static String getRealName()
        throws WebserverSystemException
    {
        return getPrincipal().getRealName();
    }

    public static String getHandle()
        throws WebserverSystemException
    {
        return (String)getAuthentication().getCredentials();
    }

    public static boolean isExternalUser()
        throws WebserverSystemException
    {
        return !(getAuthentication() instanceof EscidocRunAsInternalUserToken);
    }

    public static boolean isInternalUser()
        throws WebserverSystemException
    {
        return getAuthentication() instanceof EscidocRunAsInternalUserToken;
    }

    public static boolean isAnonymousUser()
        throws WebserverSystemException
    {
        return "".equals(getHandle());
    }

    public static boolean isIdOfAnonymousUser(String userId)
    {
        return "".equals(userId);
    }

    public static boolean isRestAccess()
        throws WebserverSystemException
    {
        return getRequestDetails().isRestAccess();
    }

    public static void setRestrictedPermissions(int restrictedPermissionCode)
        throws WebserverSystemException
    {
        getRequestDetails().setRestrictedPermissions(restrictedPermissionCode);
    }

    public static int getRestrictedPermissions()
        throws WebserverSystemException
    {
        return getRequestDetails().getRestrictedPermissions();
    }

    public static boolean isRetrieveRestrictedToReleased()
        throws WebserverSystemException
    {
        return (getRequestDetails().getRestrictedPermissions() & 1) != 0;
    }

    private static EscidocRequestDetail getRequestDetails()
        throws WebserverSystemException
    {
        Object details = getAuthentication().getDetails();
        if(details == null)
            throw new WebserverSystemException("UserContext is not initializedMissing request detail.");
        else
            return (EscidocRequestDetail)details;
    }

    private static final AppLogger LOG = new AppLogger(UserContext.class.getName());
    private static final String MISSING_REQUEST_DETAIL = "Missing request detail.";
    public static final String ANONYMOUS_IDENTIFIER = "";
    private static final String USER_CONTEXT_IS_NOT_INITIALIZED = "UserContext is not initialized";
    public static final int UNRESTRICTED_PERMISSION = 0;
    public static final int RESTRICTED_PERMISSION_RELEASES_ONLY = 1;

}
