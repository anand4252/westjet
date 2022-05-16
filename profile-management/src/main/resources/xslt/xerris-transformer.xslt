<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/">

        <xerris>
            <xsl:for-each select="employees/employee">
                <westjet>
                    <employeeDetails>
                        <id>
                            <xsl:value-of select="@id"/>
                        </id>

                        <firstName>
                            <xsl:value-of select="fname"/>
                        </firstName>

                        <role>
                            <xsl:value-of select="role"/>
                        </role>

                        <workLocation>
                            <xsl:value-of select="location"/>
                        </workLocation>
                    </employeeDetails>
                </westjet>
            </xsl:for-each>
        </xerris>

    </xsl:template>

</xsl:stylesheet>