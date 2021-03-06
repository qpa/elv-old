<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE helpset   
PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 1.0//EN"
         "http://java.sun.com/products/javahelp/helpset_1_0.dtd">

<helpset xml:lang="de">
  <!-- title -->
  <title>ELV Hilfe</title>

  <!-- maps -->
  <maps>
     <homeID>greetings</homeID>
     <mapref location="de/Elv.jhm"/>
  </maps>

  <!-- views -->
  <view mergetype="javax.help.UniteAppendMerge">
    <name>TOC</name>
    <label>Inhalt</label>
    <type>javax.help.TOCView</type>
    <data>de/ElvTOC.xml</data>
  </view>

  <view mergetype="javax.help.SortMerge">
    <name>Index</name>
    <label>Index</label>
    <type>javax.help.IndexView</type>
    <data>de/ElvIndex.xml</data>
  </view>

  <view>
    <name>Search</name>
    <label>Suche</label>
    <type>javax.help.SearchView</type>
    <data engine="com.sun.java.help.search.DefaultSearchEngine">JavaHelpSearch</data>
  </view>

  <presentation default="true" displayviewimages="false">
    <name>main window</name>
    <title>ELV Hilfe</title>
    <image>icon.elv</image>
    <toolbar>
      <helpaction image="icon.back">javax.help.BackAction</helpaction>
      <helpaction image="icon.forward">javax.help.ForwardAction</helpaction>
      <helpaction>javax.help.SeparatorAction</helpaction>
      <helpaction image="icon.home">javax.help.HomeAction</helpaction>
    </toolbar>
  </presentation>
</helpset>

