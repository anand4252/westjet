<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/">
        <xerris>
            <location>
                <xsl:for-each select="xerris/westjet/employeeDetails">

                    <xsl:choose>
                        <xsl:when test="workLocation = 'vancouver'">
                            <vancouver>
                                <name>
                                    <xsl:value-of select="firstName"/>
                                </name>
                                <role>
                                    <xsl:value-of select="role"/>
                                </role>
                            </vancouver>
                        </xsl:when>

                        <xsl:otherwise>
                            <other>
                                <name>
                                    <xsl:value-of select="firstName"/>
                                </name>
                                <role>
                                    <xsl:value-of select="role"/>
                                </role>
                            </other>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
            </location>
        </xerris>

    </xsl:template>

</xsl:stylesheet>