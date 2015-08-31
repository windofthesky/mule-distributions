/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.extension.internal.capability.xml;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import org.mule.extension.annotations.capability.Xml;
import org.mule.extension.introspection.capability.XmlCapability;
import org.mule.extension.introspection.declaration.fluent.DeclarationDescriptor;
import org.mule.module.extension.internal.introspection.AbstractCapabilitiesExtractorContractTestCase;
import org.mule.module.extension.spi.CapabilityExtractor;
import org.mule.tck.size.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@SmallTest
@RunWith(MockitoJUnitRunner.class)
public class XmlCapabilityExtractorTestCase extends AbstractCapabilitiesExtractorContractTestCase
{

    private static final String SCHEMA_VERSION = "SCHEMA_VERSION";
    private static final String NAMESPACE = "NAMESPACE";
    private static final String SCHEMA_LOCATION = "SCHEMA_LOCATION";

    private static final String EXTENSION_NAME = "extension";
    private static final String EXTENSION_VERSION = "3.7";

    @Override
    protected CapabilityExtractor createCapabilityExtractor()
    {
        return new XmlCapabilityExtractor();
    }

    @Override
    public void extractExtensionCapability()
    {
        XmlCapability capability = (XmlCapability) capabilityExtractor.extractExtensionCapability(declarationDescriptor, XmlSupport.class);

        assertThat(capability, is(notNullValue()));
        assertThat(capability.getSchemaVersion(), is(SCHEMA_VERSION));
        assertThat(capability.getNamespace(), is(NAMESPACE));
        assertThat(capability.getSchemaLocation(), is(SCHEMA_LOCATION));
    }

    @Test
    public void defaultCapabilityValues()
    {
        declarationDescriptor = spy(new DeclarationDescriptor().named(EXTENSION_NAME).onVersion(EXTENSION_VERSION));
        XmlCapability capability = (XmlCapability) capabilityExtractor.extractExtensionCapability(declarationDescriptor, DefaultXmlExtension.class);

        assertThat(capability, is(notNullValue()));
        assertThat(capability.getSchemaVersion(), is(EXTENSION_VERSION));
        assertThat(capability.getNamespace(), is(NAMESPACE));
        assertThat(capability.getSchemaLocation(), equalTo(String.format(XmlCapabilityExtractor.DEFAULT_SCHEMA_LOCATION_MASK, EXTENSION_NAME)));
    }

    @Xml(schemaVersion = SCHEMA_VERSION, namespace = NAMESPACE, schemaLocation = SCHEMA_LOCATION)
    private static class XmlSupport
    {

    }

    @Xml(namespace = NAMESPACE)
    private static class DefaultXmlExtension
    {

    }
}
