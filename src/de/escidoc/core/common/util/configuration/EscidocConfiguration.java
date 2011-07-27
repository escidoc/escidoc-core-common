// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EscidocConfiguration.java

package de.escidoc.core.common.util.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

import de.escidoc.core.common.exceptions.EscidocException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.logger.AppLogger;

public final class EscidocConfiguration
{

    private EscidocConfiguration()
        throws EscidocException
    {
        System.setProperty("java.awt.headless", "true");
    }

    public static synchronized EscidocConfiguration getInstance()
        throws IOException
    {
        if(instance == null)
            try
            {
                instance = new EscidocConfiguration();
            }
            catch(EscidocException e)
            {
                StringWriter w = new StringWriter();
                PrintWriter pw = new PrintWriter(w);
                e.printStackTrace(pw);
                throw new IOException((new StringBuilder()).append("Problem while loading properties! Caused by:\n").append(w.toString()).toString());
            }
        return instance;
    }

    public String get(String name)
    {
        return (String)properties.get(name);
    }

    public String get(String name, String defaultValue)
    {
        String prop = (String)properties.get(name);
        if(prop == null)
            prop = defaultValue;
        return prop;
    }

    public boolean getAsBoolean(String name)
    {
        boolean result = false;
        String prop = ((String)properties.get(name)).toLowerCase();
        if(prop != null && ("true".equals(prop) || "1".equals(prop)))
            result = true;
        return result;
    }

    public long getAsLong(String name)
    {
        return Long.parseLong(properties.getProperty(name));
    }

    private synchronized Properties loadProperties()
        throws SystemException
    {
        Properties result;
        try
        {
            result = getProperties("escidoc-core.properties");
        }
        catch(IOException e)
        {
            throw new SystemException("properties not found.");
        }
        if(LOG.isDebugEnabled())
            LOG.debug((new StringBuilder()).append("Default properties: ").append(result).toString());
        Properties specific = null;
        try
        {
            specific = getProperties("escidoc-core.custom.properties");
        }
        catch(IOException e)
        {
            specific = new Properties();
        }
        if(LOG.isDebugEnabled())
            LOG.debug((new StringBuilder()).append("Specific properties: ").append(specific).toString());
        result.putAll(specific);
        Properties constant = new Properties();
        try
        {
            constant = getProperties("escidoc-core.constant.properties");
        }
        catch(IOException e)
        {
            constant = new Properties();
        }
        if(LOG.isDebugEnabled())
            LOG.debug((new StringBuilder()).append("Constant properties: ").append(constant).toString());
        result.putAll(constant);
        if(LOG.isDebugEnabled())
            LOG.debug((new StringBuilder()).append("Merged properties: ").append(result).toString());
        String key;
        String value;
        for(Iterator iter = result.keySet().iterator(); iter.hasNext(); System.setProperty(key, value))
        {
            key = (String)iter.next();
            value = result.getProperty(key);
            value = replaceEnvVariables(value);
        }

        return result;
    }

    private synchronized Properties getProperties(String filename)
        throws IOException
    {
        Properties result = new Properties();
        InputStream propertiesStream = getInputStream(filename);
        result.load(propertiesStream);
        return result;
    }

    private synchronized InputStream getInputStream(String filename)
        throws FileNotFoundException
    {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if(inputStream == null)
            inputStream = new FileInputStream(new File(filename));
        return inputStream;
    }

    private synchronized String replaceEnvVariables(String property)
    {
        String replacedProperty = property;
        if(property.indexOf("${") > -1)
        {
            String envVariables[] = property.split("\\}.*?\\$\\{");
            if(envVariables != null)
            {
                for(int i = 0; i < envVariables.length; i++)
                {
                    envVariables[i] = envVariables[i].replaceFirst(".*?\\$\\{", "");
                    envVariables[i] = envVariables[i].replaceFirst("\\}.*", "");
                    if(System.getProperty(envVariables[i]) != null && !System.getProperty(envVariables[i]).equals(""))
                    {
                        String envVariable = System.getProperty(envVariables[i]);
                        envVariable = envVariable.replaceAll("\\\\", "/");
                        replacedProperty = replacedProperty.replaceAll((new StringBuilder()).append("\\$\\{").append(envVariables[i]).append("}").toString(), envVariable);
                    }
                }

            }
        }
        return replacedProperty;
    }

    public String appendToSelfURL(String path)
    {
        String selfUrl = get("escidoc-core.selfurl");
        if(selfUrl != null)
        {
            if(selfUrl.endsWith("/"))
                selfUrl = selfUrl.substring(0, selfUrl.length() - 1);
            selfUrl = (new StringBuilder()).append(selfUrl).append(path).toString();
        }
        return selfUrl;
    }

    public static final String SEARCH_PROPERTIES_DIRECTORY = "search.properties.directory";
    public static final String GSEARCH_URL = "gsearch.url";
    public static final String GSEARCH_PASSWORD = "gsearch.fedoraPass";
    public static final String FILTER_DEFAULT_LIMIT = "escidoc-core.filter.default-limit";
    public static final String FEDORA_URL = "fedora.url";
    public static final String FEDORA_USER = "fedora.user";
    public static final String BUILD_NUMBER = "escidoc-core.build";
    public static final String ADMIN_EMAIL = "escidoc-core.admin-email";
    public static final String ESCIDOC_REPOSITORY_NAME = "escidoc-core.repository-name";
    public static final String FEDORA_PASSWORD = "fedora.password";
    public static final String ESCIDOC_CORE_NOTIFY_INDEXER_ENABLED = "escidoc-core.notify.indexer.enabled";
    public static final String ESCIDOC_CORE_BASEURL = "escidoc-core.baseurl";
    public static final String ESCIDOC_CORE_SELFURL = "escidoc-core.selfurl";
    public static final String ESCIDOC_CORE_PROXY_HOST = "escidoc-core.proxyHost";
    public static final String ESCIDOC_CORE_PROXY_PORT = "escidoc-core.proxyPort";
    public static final String ESCIDOC_CORE_NON_PROXY_HOSTS = "escidoc-core.nonProxyHosts";
    public static final String ESCIDOC_CORE_XSD_PATH = "escidoc-core.xsd-path";
    public static final String ESCIDOC_CORE_OM_CONTENT_CHECKSUM_ALGORITHM = "escidoc-core.om.content.checksum-algorithm";
    public static final String ESCIDOC_CORE_XSLT_STD = "escidoc-core.xslt.std";
    public static final String ESCIDOC_CORE_IDENTIFIER_PREFIX = "escidoc-core.identifier.prefix";
    public static final String ESCIDOC_CORE_PID_SYSTEM_FACTORY = "escidoc-core.PidSystemFactory";
    public static final String ESCIDOC_CORE_DUMMYPID_GLOBALPREFIX = "escidoc-core.dummyPid.globalPrefix";
    public static final String ESCIDOC_CORE_DUMMYPID_LOCALPREFIX = "escidoc-core.dummyPid.localPrefix";
    public static final String ESCIDOC_CORE_DUMMYPID_NAMESPACE = "escidoc-core.dummyPid.pidNamespace";
    public static final String ESCIDOC_CORE_DUMMYPID_SEPARATOR = "escidoc-core.dummyPid.separator";
    public static final String ESCIDOC_CORE_USERHANDLE_LIFETIME = "escidoc-core.userHandle.lifetime";
    public static final String ESCIDOC_CORE_USERHANDLE_COOKIE_LIFETIME = "escidoc-core.userHandle.cookie.lifetime";
    public static final String ESCIDOC_CORE_USERHANDLE_COOKIE_VERSION = "escidoc-core.userHandle.cookie.version";
    public static final String ESCIDOC_CORE_PID_SERVICE_HOST = "escidoc-core.PidSystemRESTService.host";
    public static final String ESCIDOC_CORE_PID_RESTSERVICE_USER = "escidoc-core.PidSystemRESTService.user";
    public static final String ESCIDOC_CORE_PID_RESTSERVICE_PASSWORD = "escidoc-core.PidSystemRESTService.password";
    public static final String ESCIDOC_CORE_PID_GLOBALPREFIX = "escidoc-core.PidSystem.globalPrefix";
    public static final String ESCIDOC_CORE_PID_LOCALPREFIX = "escidoc-core.PidSystem.localPrefix";
    public static final String ESCIDOC_CORE_PID_NAMESPACE = "escidoc-core.PidSystem.namespace";
    public static final String ESCIDOC_CORE_PID_SEPARATOR = "escidoc-core.PidSystem.separator";
    public static final String DE_FIZ_ESCIDOC_OM_SERVICE_PROVIDER_URL = "de.escidoc.core.om.service.provider.url";
    public static final String DE_FIZ_ESCIDOC_SM_SERVICE_PROVIDER_URL = "de.escidoc.core.sm.service.provider.url";
    public static final String ESCIDOC_CORE_DEFAULT_JNDI_URL = "escidoc-core.default.jndi.url";
    public static final String SM_FRAMEWORK_SCOPE_ID = "sm.framework.scope.id";
    public static final String ESCIDOC_CORE_QUEUE_USER = "escidoc-core.queue.user";
    public static final String ESCIDOC_CORE_QUEUE_PASSWORD = "escidoc-core.queue.password";
    public static final String ESCIDOC_CORE_DATASOURCE_INDEX_PREFIX_LENGTH = "escidoc-core.datasource.index.prefix.length";
    public static final String CONTENT_RELATIONS_URL = "escidoc-core.ontology.url";
    public static final String ESCIDOC_CORE_AA_CACHE_ROLES_SIZE = "escidoc-core.aa.cache.roles.size";
    public static final String ESCIDOC_CORE_AA_CACHE_USERS_SIZE = "escidoc-core.aa.cache.users.size";
    public static final String ESCIDOC_CORE_AA_CACHE_GROUPS_SIZE = "escidoc-core.aa.cache.groups.size";
    public static final String ESCIDOC_CORE_AA_CACHE_RESOURCES_IN_ROLE_IS_GRANTED_SIZE = "escidoc-core.aa.cache.resources-in-role-is-granted.size";
    public static final String ESCIDOC_CORE_AA_CACHE_ATTRIBUTES_SIZE = "escidoc-core.aa.cache.attributes.size";
    public static final String ESCIDOC_CORE_AA_OU_ATTRIBUTE_NAME = "escidoc-core.aa.attribute-name.ou";
    public static final String ESCIDOC_CORE_AA_COMMON_NAME_ATTRIBUTE_NAME = "escidoc-core.aa.attribute-name.common-name";
    public static final String ESCIDOC_CORE_AA_PERSISTENT_ID_ATTRIBUTE_NAME = "escidoc-core.aa.attribute-name.persistent-id";
    public static final String ESCIDOC_CORE_INDEXER_CACHE_SIZE = "escidoc-core.om.indexer.cache.size";
    public static final String DIGILIB_SCALER = "digilib.scaler";
    public static final String DIGILIB_CLIENT = "digilib.digimage";
    private static final AppLogger LOG = new AppLogger(EscidocConfiguration.class.getName());
    private static EscidocConfiguration instance = null;
    private final Properties properties = loadProperties();

}
