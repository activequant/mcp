<?xml version="1.0"?>
<!--
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.    
-->

<!--    XSLT stylesheet to convert the Fop documentation collected in one xml file into a fo file
        for use in FOP

TBD: - The faq doesn't show in the content
     - check why margin-bottom on the page with properties is too large
     - check why keep-next not only doesn't work, but leads to repeating already printed lines
     - make lines containing only code look nicer (smaller line height)
     - replace bullets in ordered lists with numbers
     - correct the hack replacing nbsp with '-'
     - handle the links correctly which have been external in the html doc and are now internal

-->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">

<xsl:template match ="/">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <!-- defines page layout -->

    <fo:layout-master-set>

        <fo:simple-page-master master-name="first"
            page-height="29.7cm"
            page-width="21cm"
            margin-top="1.5cm"
            margin-bottom="2cm"
            margin-left="2.5cm"
            margin-right="2.5cm">

            <fo:region-body margin-top="3cm"/>
            <fo:region-before extent="1.5cm"/>
            <fo:region-after extent="1.5cm"/>
        </fo:simple-page-master>

        <fo:simple-page-master master-name="rest"
            page-height="29.7cm"
            page-width="21cm"
            margin-top="1.5cm"
            margin-bottom="2cm"
            margin-left="2.5cm"
            margin-right="2.5cm">

            <fo:region-body margin-top="2.5cm"/>
            <fo:region-before extent="1.5cm"/>
            <fo:region-after extent="1.5cm"/>
        </fo:simple-page-master>

        <fo:page-sequence-master master-name="all">
            <fo:single-page-master-reference master-name="first"/>
            <fo:repeatable-page-master-reference master-name="rest"/>
        </fo:page-sequence-master>

    </fo:layout-master-set>

    <fo:page-sequence master-name="all">
        <fo:static-content flow-name="xsl-region-before">
            <fo:block text-align="end"
                font-size="10pt"
                font-family="serif"
                line-height="14pt" >

                Velocity User's Guide - pg

                <fo:page-number/>
            </fo:block>
        </fo:static-content>

        <fo:flow flow-name="xsl-region-body">

            <fo:block font-size="18pt"
                font-family="sans-serif"
                line-height="24pt"
                space-after.optimum="15pt"
                background-color="blue"
                color="white"
                text-align="center">
                Velocity
            </fo:block>


            <!-- generates table of contents and puts it into a table -->

            <fo:block font-size="14pt"
                font-family="sans-serif"
                line-height="18pt"
                space-after.optimum="10pt"
                font-weight="bold"
                start-indent="15pt">
                Content
            </fo:block>

            <fo:table>
                <fo:table-column column-width="1cm"/>
                <fo:table-column column-width="15cm"/>
                <fo:table-body font-size="12pt"
                    line-height="16pt"
                    font-family="sans-serif">

                    <xsl:for-each select="//document">
                        <fo:table-row>
                            <fo:table-cell/>
                            <fo:table-cell>
                                <fo:block  text-align="start" >
                                    <xsl:value-of select="header/title"/>
                                </fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <xsl:call-template name="content">
                            <xsl:with-param name="doc" select="body"/>
                        </xsl:call-template>
                    </xsl:for-each>
                </fo:table-body>
            </fo:table>
            <xsl:apply-templates select="documentation"/>
            </fo:flow>
        </fo:page-sequence>
    </fo:root>
</xsl:template>

<!-- s1 -->
<xsl:template match ="s1">
    <fo:block font-size="18pt"
        font-family="sans-serif"
        line-height="24pt"
        space-before.optimum="15pt"
        space-after.optimum="15pt"
        background-color="blue"
        color="white"
        text-align="center">

        <xsl:attribute name="id">
            <xsl:value-of select="translate(.,' ),-.(','____')"/>
        </xsl:attribute>

        <xsl:value-of select="@title"/>
    </fo:block>
    <xsl:apply-templates/>
</xsl:template>

<!-- s2 -->
<xsl:template match ="s2">
    <fo:block font-size="16pt"
        font-family="sans-serif"
        line-height="20pt"
        space-before.optimum="15pt"
        space-after.optimum="12pt"
        text-align="center"
        padding-top="3pt">

        <xsl:value-of select="@title"/>

    </fo:block>
    <xsl:apply-templates/>
</xsl:template>

<!-- s3 -->
<xsl:template match ="s3">
    <fo:block font-size="14pt"
        font-family="sans-serif"
        line-height="18pt"
        space-before.optimum="10pt"
        space-after.optimum="9pt"
        text-align="center"
        padding-top="3pt">

        <xsl:value-of select="@title"/>

    </fo:block>
    <xsl:apply-templates/>
</xsl:template>

<!-- p  [not(code)] -->
<xsl:template match ="p">
    <fo:block font-size="11pt"
        font-family="sans-serif"
        line-height="13pt"
        space-after.optimum="3pt"
        space-before.optimum="3pt"
        text-align="justify">

        <xsl:apply-templates/>

   </fo:block>
</xsl:template>

<!-- p + code
<xsl:template match ="p[code]">
    <fo:block font-size="11pt"
        font-family="sans-serif"
        line-height="11pt"
        space-after.optimum="0pt"
        space-before.optimum="0pt"
        text-align="start">

        <xsl:apply-templates/>

   </fo:block>
</xsl:template>
-->

<!-- faqs -->
<xsl:template match ="faqs">
    <fo:block font-size="18pt"
        font-family="sans-serif"
        line-height="24pt"
        space-before.optimum="15pt"
        space-after.optimum="15pt"
        background-color="blue"
        color="white"
        text-align="center">

        <xsl:attribute name="id">
            <xsl:value-of select="translate(.,' ),-.(','____')"/>
        </xsl:attribute>

        <xsl:value-of select="@title"/>

    </fo:block>
    <xsl:apply-templates/>
</xsl:template>

<xsl:template match ="strong">
    <fo:block font-size="12pt"
        font-family="sans-serif"
        line-height="14pt"
        space-after.optimum="3pt"
        space-before.optimum="3pt"
        text-align="start"
        font-weight="bold">

        <xsl:apply-templates/>

   </fo:block>
</xsl:template>

<!-- faq -->
<xsl:template match ="faq">
    <xsl:apply-templates/>
</xsl:template>

<!-- q in faq -->
<xsl:template match ="q">
    <fo:block font-size="11pt"
        font-family="sans-serif"
        line-height="13pt"
        space-after.optimum="3pt"
        space-before.optimum="3pt"
        text-align="justify">

        <xsl:apply-templates/>

    </fo:block>
</xsl:template>

<!-- a in faq -->
<xsl:template match ="a">
      <xsl:apply-templates/>
</xsl:template>


<!-- jump (links) -->
<xsl:template match ="*/jump">
    <fo:simple-link color="blue" external-destination="{@href}">
        <xsl:apply-templates/>
    </fo:simple-link>
</xsl:template>


<!-- code
<xsl:template match ="*/code">
   <fo:inline font-size="10pt"
            font-family="Courier">
     <xsl:apply-templates/>
   </fo:inline>
</xsl:template>
-->

<xsl:template match ="*/source">
   <fo:inline font-size="10pt"
            font-family="Courier"
            white-space-treatment="preserve">
     <xsl:apply-templates/>
   </fo:inline>
</xsl:template>

<!-- p + source -->
<xsl:template match ="p[source]">
   <fo:block font-size="11pt"
            font-family="sans-serif"
            line-height="12pt"
            space-after.optimum="0pt"
            space-before.optimum="0pt"
            text-align="start">
     <xsl:apply-templates/>
   </fo:block>
</xsl:template>



<!-- ul (unordered list) -->
<xsl:template match ="ul">
  <fo:list-block start-indent="1cm"
                 provisional-distance-between-starts="12pt"
                 font-family="sans-serif"
                 font-size="11pt"
                 line-height="11pt">
     <xsl:apply-templates/>
   </fo:list-block>
</xsl:template>


<!-- ol (ordered list) -->
<xsl:template match ="ol">
  <fo:list-block start-indent="1cm"
                 provisional-distance-between-starts="12pt"
                 font-family="sans-serif"
                 font-size="11pt"
                 line-height="11pt">
     <xsl:apply-templates/>
   </fo:list-block>
</xsl:template>


<!-- li (list item) in unordered list -->
<xsl:template match ="ul/li">
    <fo:list-item>
      <fo:list-item-label>
        <fo:block><fo:inline font-family="Symbol">&#183;</fo:inline></fo:block>
      </fo:list-item-label>
      <fo:list-item-body>
        <fo:block space-after.optimum="4pt"
              text-align="justify"
              padding-top="3pt">
          <xsl:apply-templates/>
       </fo:block>
      </fo:list-item-body>
    </fo:list-item>
</xsl:template>


<!-- li (list item) in ordered list -->
<xsl:template match ="ol/li">
    <fo:list-item>
      <fo:list-item-label>
        <fo:block>
          <xsl:number level="multiple" count="li" format="1"/>)
        </fo:block>
      </fo:list-item-label>
      <fo:list-item-body>
        <fo:block space-after.optimum="4pt"
              text-align="justify"
              padding-top="3pt">
          <xsl:apply-templates/>
       </fo:block>
      </fo:list-item-body>
    </fo:list-item>
</xsl:template>

<xsl:template match="table">
    <fo:table>
        <xsl:for-each select="tr[1]/td">
            <fo:table-column column-width="1.7in"/>
        </xsl:for-each>
        <fo:table-body font-size="10pt"
                    line-height="14pt"
                    font-family="sans-serif"
                    background-color="#a0ddf0">

            <xsl:for-each select="tr">
                <fo:table-row>
                    <xsl:for-each select="td">
                        <fo:table-cell>
                            <fo:block  text-align="start" >
                                <xsl:value-of select="."/>
                            </fo:block>
                        </fo:table-cell>
                     </xsl:for-each>
                 </fo:table-row>
             </xsl:for-each>
         </fo:table-body>
    </fo:table>
</xsl:template>

<xsl:template name="content">
    <xsl:param name="doc"/>
    <xsl:param name="prefix">...</xsl:param>
    <xsl:for-each select="$doc/s1">
        <fo:table-row>
            <fo:table-cell/>
            <fo:table-cell>
                <fo:block  text-align="start" font-size="10pt">
                    <fo:simple-link color="blue">
                        <xsl:attribute name="internal-destination">
                            <xsl:value-of select="translate(.,' ),-.(','____')"/>
                        </xsl:attribute>
                        <xsl:value-of select="$prefix"/>
                        <xsl:value-of select="@title"/>
                    </fo:simple-link>
                </fo:block>
           </fo:table-cell>
        </fo:table-row>
        <xsl:call-template name="content">
            <xsl:with-param name="doc" select="."/>
            <xsl:with-param name="prefix" select="concat($prefix,'...')"/>
        </xsl:call-template>
    </xsl:for-each>
</xsl:template>
<!-- end body -->

</xsl:stylesheet>
