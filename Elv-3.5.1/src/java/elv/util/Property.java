/*
 * Property.java
 */
package elv.util;

/**
 * Class for reprezenting a property.
 * @author Qpa
 */
public class Property<V> implements java.io.Serializable, java.lang.Cloneable
{
  
  /**
   * Constants.
   */
  // Names.
  public final static java.lang.String NAME = "Property";
  public final static java.lang.String PROPERTIES_NAME = "Properties";
  // Values.
  public final static java.lang.String BOOLEAN = "B";
  public final static java.lang.String INTEGER = "I";
  public final static java.lang.String INTEGER_ARRAY = "[I";
  public final static java.lang.String DOUBLE = "F";
  public final static java.lang.String DOUBLE_ARRAY = "[F";
  public final static java.lang.String STRING = "S";
  public final static java.lang.String STRING_ARRAY = "[S";
  public final static java.lang.String POINT = "P";
  public final static java.lang.String POINT_ARRAY = "[P";
  public final static java.lang.String DIMENSION = "D";
  public final static java.lang.String DIMENSION_ARRAY = "[D";
  public final static java.lang.String DATE = "DT";
  public final static java.lang.String LOCALE = "L";
  public final static java.lang.String LOCALE_ARRAY = "[L";
  public final static java.lang.String STATE = "ST";
  public final static java.lang.String GENDER_ARRAY = "[G";
  public final static java.lang.String TASK = "T";
  
  public final static java.lang.String NO_FIELD = "NF";
  public final static java.lang.String LABEL_FIELD = "LF";
  public final static java.lang.String TEXT_FIELD = "TF";
  public final static java.lang.String PASSWORD_FIELD = "PF";
  public final static java.lang.String SPINNER = "SF";
  public final static java.lang.String COMBO_BOX = "CB";
  public final static java.lang.String DATE_BUTTON = "DB";
  public final static java.lang.String LIST_BUTTON = "LB";
  public final static java.lang.String TABLE_BUTTON = "TB";
  public final static java.lang.String TREE_BUTTON = "TRB";
  
  public final static java.lang.String ARRAY_SEPARATOR = ";";
  
  /**
   * Variables.
   */
  private java.lang.String type;
  private java.lang.String editorType;
  private boolean haveToTranslate;
  private java.lang.String name;
  private V value;
  private java.util.Vector<V> defaultValues;
  
  /**
   * Constructor for stored property.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any error with parsing.
   */
  public Property(java.lang.String line) throws java.lang.Exception
  {
    //Line:  type|editorType|haveToTranslate|name=value
    java.lang.String tehn = line.substring(0, line.indexOf("="));
    java.lang.String v = line.substring(line.indexOf("=") + 1);
    java.util.StringTokenizer sT = new java.util.StringTokenizer(tehn, "|");
    type = sT.nextToken();
    editorType = sT.nextToken();
    haveToTranslate = java.lang.Boolean.valueOf(sT.nextToken()).booleanValue();
    name = sT.nextToken();
    parse(v);
  }
    
  /**
   * Constructor for brand new property.
   * @param type the type of property, one of constants: INTEGER, ..., STRING.
   * @param editorType the editor field of property, one of constants: TEXT_FIELD, ..., TABLE_BUTTON.
   * @param haveToTranslate if property value needs international translation.
   * @param name the name of property.
   * @param value the value of property.
   */
  public Property(java.lang.String type, java.lang.String editorType, boolean haveToTranslate, java.lang.String name, V value)
  {
    this.type = type;
//    this.type = value.getClass().getName();
    this.editorType = editorType;
    this.haveToTranslate = haveToTranslate;
    this.name = name;
    this.value = value;
  }
  
  /**
   * Method for getting the type of this property.
   * @return the <CODE>type</CODE> variable.
   */
  public java.lang.String getType()
  {
    return type;
  }
  
  /**
   * Method for getting the element type of this property.
   * @return the type of the element, if this property is an array type, the <CODE>type</CODE> variable, otherwise.
   */
  public java.lang.String getElementType()
  {
    java.lang.String elementType = type;
    if(type.indexOf("[") >= 0)
    {
      elementType = type.substring(type.indexOf("[") + 1);
    }
    return elementType;
  }
  
  /**
   * Method for getting the editor type of this property.
   * @return the <CODE>editorType</CODE> variable.
   */
  public java.lang.String getEditorType()
  {
    return editorType;
  }
  
  /**
   * Method for setting the editor type of this property.
   * @param the new <CODE>editorType</CODE> variable.
   */
  public void setEditorType(java.lang.String editorType)
  {
    this.editorType = editorType;
  }
  
  /**
   * Method for determining if the property value has to be translated.
   * @return the <CODE>haveToTranslate</CODE> variable
   */
  public boolean haveToTranslate()
  {
    return haveToTranslate;
  }
  
  /**
   * Method for getting the name of this property.
   * @return the <CODE>name</CODE> variable.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method for getting the value of this property.
   * @return the <CODE>value</CODE> variable.
   */
  public V getValue()
  {
    return value;
  }
  
  /**
   * Method for setting the value of this property.
   * @param the new <CODE>value</CODE> variable.
   */
  public void setValue(V value)
  {
    this.value = value;
  }
  
  /**
   * Method for getting the default values of this property.
   * @return the <CODE>defaultValues</CODE> variable.
   */
  public java.util.Vector<V> getDefaultValues()
  {
    return defaultValues;
  }
  
  /**
   * Method for setting the default values of this property.
   * @param the new <CODE>defaultValues</CODE> variable.
   */
  public void setDefaultValues(java.util.Vector<V> defaultValues)
  {
    this.defaultValues = defaultValues;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param property an <CODE>elv.util.Property</CODE> object.
   * @return true, if this property equals with the given property.
   */
  public boolean equals(java.lang.Object property)
  {
    boolean isEqual = false;
    if(property != null && property instanceof elv.util.Property)
    {
      isEqual = name.equals(((elv.util.Property)property).getName());
    }
    return isEqual;
  }
  
  /**
   * Method for stored line reprezentation.
   * @return a string with the line reprezentation.
   * @throws java.lang.Exception if there is any error with formatting.
   */
  public java.lang.String toLine() throws java.lang.Exception
  {
    return type + "|" + editorType + "|" + java.lang.Boolean.toString(haveToTranslate) + "|" + name + "=" + formatLine();
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the formatted string reprezentation of this property.
   */
  public java.lang.String toString()
  {
    java.lang.String string = null;
    try
    {
      string = formatString();
    }
    catch(java.lang.Exception exc)
    {
      string = "Format error!";
      exc.printStackTrace();
    }
    return string;
  }
  
  /**
   * Method for parsing the string reprezentation of value.
   * @param the string reprezentation of value.
   * @throws java.lang.Exception if there is any error with parsing.
   */
  private void parse(java.lang.String stringValue) throws java.lang.Exception
  {
    if(stringValue == null || stringValue.equals(""))
    {
      value = null;
    }
    else if(type.equals(BOOLEAN))
    {
      value = (V)(new java.lang.Boolean(stringValue));
    }
    else if(type.equals(INTEGER))
    {
      value = (V)(new java.lang.Integer(stringValue));
    }
    else if(type.equals(INTEGER_ARRAY))
    {
      java.util.Vector<java.lang.Integer> values = new java.util.Vector<java.lang.Integer>();
      for(java.util.StringTokenizer sT = new java.util.StringTokenizer(stringValue, ARRAY_SEPARATOR); sT.hasMoreElements(); )
      {
        values.add(java.lang.Integer.parseInt(sT.nextToken()));
      }
      value = (V)values;
    }
    else if(type.equals(DOUBLE))
    {
      value = (V)( new java.lang.Double(stringValue));
    }
    else if(type.equals(DOUBLE_ARRAY))
    {
      java.util.Vector<java.lang.Double> values = new java.util.Vector<java.lang.Double>();
      for(java.util.StringTokenizer sT = new java.util.StringTokenizer(stringValue, ARRAY_SEPARATOR); sT.hasMoreElements(); )
      {
        values.add(java.lang.Double.parseDouble(sT.nextToken()));
      }
      value = (V)values;
    }
    else if(type.equals(POINT))
    {
      value = (V)Util.parsePoint(stringValue);
    }
    else if(type.equals(POINT_ARRAY))
    {
      java.util.Vector<java.awt.Point> values = new java.util.Vector<java.awt.Point>();
      for(java.util.StringTokenizer sT = new java.util.StringTokenizer(stringValue, ARRAY_SEPARATOR); sT.hasMoreElements(); )
      {
        values.add(Util.parsePoint(sT.nextToken()));
      }
      value = (V)values;
    }
    else if(type.equals(DIMENSION))
    {
      value = (V)Util.parseDimension(stringValue);
    }
    else if(type.equals(DIMENSION_ARRAY))
    {
      java.util.Vector<java.awt.Dimension> values = new java.util.Vector<java.awt.Dimension>();
      for(java.util.StringTokenizer sT = new java.util.StringTokenizer(stringValue, ARRAY_SEPARATOR); sT.hasMoreElements(); )
      {
        values.add(Util.parseDimension(sT.nextToken()));
      }
      value = (V)values;
    }
    else if(type.equals(STRING))
    {
      value = (V)stringValue;
    }
    else if(type.equals(STRING_ARRAY))
    {
      java.util.Vector<java.lang.String> values = new java.util.Vector<java.lang.String>();
      for(java.util.StringTokenizer sT = new java.util.StringTokenizer(stringValue, ARRAY_SEPARATOR); sT.hasMoreElements(); )
      {
        values.add(sT.nextToken());
      }
      value = (V)values;
    }
    else if(type.equals(DATE))
    {
      value = (V)Util.parseDate(stringValue);
    }
    else if(type.equals(LOCALE))
    {
      value = (V)Util.parseLocale(stringValue);
    }
    else if(type.equals(LOCALE_ARRAY))
    {
      java.util.Vector<java.util.Locale> values = new java.util.Vector<java.util.Locale>();
      for(java.util.StringTokenizer sT = new java.util.StringTokenizer(stringValue, ARRAY_SEPARATOR); sT.hasMoreElements(); )
      {
        values.add(Util.parseLocale(sT.nextToken()));
      }
      value = (V)values;
    }
    else if(type.equals(STATE))
    {
      value = (V)(new State(stringValue));
    }
    else if(type.equals(GENDER_ARRAY))
    {
      java.util.Vector<elv.util.parameters.Gender> values = new java.util.Vector<elv.util.parameters.Gender>();
      for(java.util.StringTokenizer sT = new java.util.StringTokenizer(stringValue, ARRAY_SEPARATOR); sT.hasMoreElements(); )
      {
        values.add(new elv.util.parameters.Gender(sT.nextToken()));
      }
      value = (V)values;
    }
    else if(type.equals(TASK))
    {
      value = (V)elv.task.Task.parse(stringValue);
    }
  }
  
  /**
   * Method for formatting the string reprezentation of value to storing.
   * @return the formatted string reprezentation of value.
   * @throws java.lang.Exception if there is any error with formatting.
   */
  private java.lang.String formatLine() throws java.lang.Exception
  {
    java.lang.String formattedValue = "";
    if(value == null)
    {
      formattedValue = "";
    }
    else if(type.equals(BOOLEAN))
    {
      formattedValue = java.lang.Boolean.toString((java.lang.Boolean)value);
    }
    else if(type.equals(INTEGER))
    {
      formattedValue = java.lang.Integer.toString((java.lang.Integer)value);
    }
    else if(type.equals(INTEGER_ARRAY))
    {
      int count = 0;
      for(int i : (java.util.Vector<java.lang.Integer>)value)
      {
        formattedValue += (count == 0 ? java.lang.Integer.toString(i) : ARRAY_SEPARATOR + java.lang.Integer.toString(i));
        count++;
      }
    }
    else if(type.equals(DOUBLE))
    {
      formattedValue = java.lang.Double.toString((java.lang.Double)value);
    }
    else if(type.equals(DOUBLE_ARRAY))
    {
      int count = 0;
      for(double i : (java.util.Vector<java.lang.Double>)value)
      {
        formattedValue += (count == 0 ? java.lang.Double.toString(i) : ARRAY_SEPARATOR + java.lang.Double.toString(i));
        count++;
      }
    }
    else if(type.equals(POINT))
    {
      formattedValue = Util.toString((java.awt.Point)value);
    }
    else if(type.equals(POINT_ARRAY))
    {
      int count = 0;
      for(java.awt.Point i : (java.util.Vector<java.awt.Point>)value)
      {
        formattedValue += (count == 0 ? Util.toString(i) : ARRAY_SEPARATOR + Util.toString(i));
        count++;
      }
    }
    else if(type.equals(DIMENSION))
    {
      formattedValue = Util.toString((java.awt.Dimension)value);
    }
    else if(type.equals(DIMENSION_ARRAY))
    {
      int count = 0;
      for(java.awt.Dimension i : (java.util.Vector<java.awt.Dimension>)value)
      {
        formattedValue += (count == 0 ? Util.toString(i) : ARRAY_SEPARATOR + Util.toString(i));
        count++;
      }
    }
    else if(type.equals(STRING))
    {
      formattedValue = (java.lang.String)value;
    }
    else if(type.equals(STRING_ARRAY))
    {
      int count = 0;
      for(java.lang.String i : (java.util.Vector<java.lang.String>)value)
      {
        formattedValue += (count == 0 ? i : ARRAY_SEPARATOR + i);
        count++;
      }
    }
    else if(type.equals(DATE))
    {
      formattedValue = Util.toString((java.util.Date)value);
    }
    else if(type.equals(LOCALE))
    {
      formattedValue = ((java.util.Locale)value).toString();
    }
    else if(type.equals(LOCALE_ARRAY))
    {
      int count = 0;
      for(java.util.Locale i : (java.util.Vector<java.util.Locale>)value)
      {
        formattedValue += (count == 0 ? i.toString() : ARRAY_SEPARATOR + i.toString());
        count++;
      }
    }
    else if(type.equals(STATE))
    {
      formattedValue = ((State)value).getName();
    }
    else if(type.equals(GENDER_ARRAY))
    {
      int count = 0;
      for(elv.util.parameters.Gender i : (java.util.Vector<elv.util.parameters.Gender>)value)
      {
        formattedValue += (count == 0 ? i.getName() : ARRAY_SEPARATOR + i.getName());
        count++;
      }
    }
    else if(type.equals(TASK))
    {
      formattedValue = ((elv.task.Task)value).toLine();
    }
    else
    {
      formattedValue = value.toString();
    }
    return formattedValue;
  }
  
  /**
   * Method for formatting the string reprezentation of value to visualize.
   * @return the formatted string reprezentation of value.
   * @throws java.lang.Exception if there is any error with formatting.
   */
  private java.lang.String formatString() throws java.lang.Exception
  {
    java.lang.String formattedValue = "";
    if(value == null)
    {
      formattedValue = "";
    }
    else if(type.equals(BOOLEAN))
    {
      formattedValue = Util.translate(java.lang.Boolean.toString((java.lang.Boolean)value));
    }
    else if(type.equals(INTEGER))
    {
      formattedValue = Util.format((java.lang.Integer)value);
    }
    else if(type.equals(INTEGER_ARRAY))
    {
      int count = 0;
      for(int i : (java.util.Vector<java.lang.Integer>)value)
      {
        formattedValue += (count == 0 ? Util.format(i) : ARRAY_SEPARATOR + " " + Util.format(i));
        count++;
      }
    }
    else if(type.equals(DOUBLE))
    {
      formattedValue = Util.format((java.lang.Double)value);
    }
    else if(type.equals(DOUBLE_ARRAY))
    {
      int count = 0;
      for(double i : (java.util.Vector<java.lang.Double>)value)
      {
        formattedValue += (count == 0 ? Util.format(i) : ARRAY_SEPARATOR + " " + Util.format(i));
        count++;
      }
    }
    else if(type.equals(POINT))
    {
      formattedValue = Util.toString((java.awt.Point)value);
    }
    else if(type.equals(POINT_ARRAY))
    {
      int count = 0;
      for(java.awt.Point i : (java.util.Vector<java.awt.Point>)value)
      {
        formattedValue += (count == 0 ? Util.toString(i) : ARRAY_SEPARATOR + " " + Util.toString(i));
        count++;
      }
    }
    else if(type.equals(DIMENSION))
    {
      formattedValue = Util.toString((java.awt.Dimension)value);
    }
    else if(type.equals(DIMENSION_ARRAY))
    {
      int count = 0;
      for(java.awt.Dimension i : (java.util.Vector<java.awt.Dimension>)value)
      {
        formattedValue += (count == 0 ? Util.toString(i) : ARRAY_SEPARATOR + Util.toString(i));
        count++;
      }
    }
    else if(type.equals(STRING))
    {
      java.lang.String passwordValue = java.lang.String.valueOf(new char[]{Util.PASSWORD_ECHO_CHAR, Util.PASSWORD_ECHO_CHAR, Util.PASSWORD_ECHO_CHAR, Util.PASSWORD_ECHO_CHAR, Util.PASSWORD_ECHO_CHAR, Util.PASSWORD_ECHO_CHAR});
      formattedValue = (haveToTranslate ? Util.translate((java.lang.String)value): (editorType.equals(PASSWORD_FIELD) ? passwordValue : (java.lang.String)value));
    }
    else if(type.equals(STRING_ARRAY))
    {
      int count = 0;
      for(java.lang.String i : (java.util.Vector<java.lang.String>)value)
      {
        formattedValue += (count == 0 ? (haveToTranslate ? Util.translate(i) : i) : ARRAY_SEPARATOR + " " + (haveToTranslate ? Util.translate(i) : i));
        count++;
      }
    }
    else if(type.equals(DATE))
    {
      formattedValue = Util.format((java.util.Date)value);
    }
    else if(type.equals(LOCALE))
    {
      formattedValue = ((java.util.Locale)value).getDisplayName((java.util.Locale)value);
    }
    else if(type.equals(LOCALE_ARRAY))
    {
      int count = 0;
      for(java.util.Locale i : (java.util.Vector<java.util.Locale>)value)
      {
        formattedValue += (count == 0 ? i.getDisplayName(i) : ARRAY_SEPARATOR + " " + i.getDisplayName(i));
        count++;
      }
    }
    else if(type.equals(STATE))
    {
      formattedValue = ((State)value).toString();
    }
    else if(type.equals(GENDER_ARRAY))
    {
      int count = 0;
      for(elv.util.parameters.Gender i : (java.util.Vector<elv.util.parameters.Gender>)value)
      {
        formattedValue += (count == 0 ? i.toString() : ARRAY_SEPARATOR + " " + i.toString());
        count++;
      }
    }
    else if(type.equals(TASK))
    {
      formattedValue = ((elv.task.Task)value).getTitle();
    }
    else
    {
      formattedValue = value.toString();
    }
    return formattedValue;
  }
  
  /**
   * Method for getting a property by name.
   * @param name the name of property.
   * @param properties the list of properties.
   * @return the named property.
   * @throws java.lang.Exception if there is any error with the getting.
   */
  public static Property get(java.lang.String name, java.util.Vector<Property> properties) throws java.lang.Exception
  {
    Property property = null;
    for(Property iteratorProperty : properties)
    {
      if(iteratorProperty.getName().equals(name))
      {
       property = iteratorProperty;
       break;
      }
    }
    return property;
  }
  
  /**
   * Method for getting a list of related properties.
   * @param pattern the related pattern.
   * @param properties a vector of properties.
   * @return a vector with the related properties.
   * @throws java.lang.Exception if there is any error with the relationship.
   */
  public static java.util.Vector<Property> getRelatives(java.lang.String pattern, java.util.Vector<Property> properties) throws java.lang.Exception
  {
    java.util.Vector<Property> relatives = new java.util.Vector<Property>();
    int c = 0;
    for(Property iteratorProperty : properties)
    {
      if(iteratorProperty.getName().matches(".*" + pattern + ".*"))
      {
        relatives.add(iteratorProperty);
      }
    }
    return relatives;
  }
  
  /**
   * Method for making a clone vector.
   * @param properties a vector of properties.
   * @return the created clone vector.
   */
  public synchronized static java.util.Vector<Property> clone(java.util.Vector<Property> properties)
  {
    java.util.Vector<Property> clone = new java.util.Vector<Property>();
    for(elv.util.Property iteratorProperty : properties)
    {
      Property property = new Property(iteratorProperty.getType(), iteratorProperty.getEditorType(), iteratorProperty.haveToTranslate(), iteratorProperty.getName(), iteratorProperty.getValue());
      java.util.Vector defaultValues = iteratorProperty.getDefaultValues();
      property.setDefaultValues((defaultValues != null ? (java.util.Vector)defaultValues.clone() : null));
      clone.add(property);
    }
    return clone;
  }
  
  /**
   * Method for loading the properties from a file.
   * @param containerPath the full path name of the parameter container (which could be a directory or an archive file).
   * @param name the parameter file name (the name of the file in case of a directory, or the zip entry name in case if an archive).
   * @return a vector of properties.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized static java.util.Vector<Property> load(java.lang.String containerPath, java.lang.String name) throws java.lang.Exception
  {
    java.util.Vector<Property> properties = new java.util.Vector<Property>();
    java.lang.String line;
    java.io.File container = new java.io.File(containerPath);
    if(!container.exists())
    {
      throw new java.io.FileNotFoundException(containerPath);
    }
    if(container.isDirectory()) // Directory.
    {
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(containerPath + elv.util.Util.getFS() + name), elv.util.Util.FILE_ENCODING));
      while((line = fileReader.readLine()) != null)
      {
        properties.add(new elv.util.Property(line));
      }
      fileReader.close();
    }
    else
    {
      java.util.zip.ZipFile archiveZipFile = new java.util.zip.ZipFile(containerPath);
      for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
      {
        java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
        if(!iteratorEntry.isDirectory())
        {
          if(iteratorEntry.getName().equals(name))
          {
            java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(archiveZipFile.getInputStream(iteratorEntry), elv.util.Util.FILE_ENCODING));
            while((line = fileReader.readLine()) != null)
            {
              properties.add(new elv.util.Property(line));
            }
            fileReader.close();
            archiveZipFile.close();
            break;
          }
        }
      }
      archiveZipFile.close();
    }
    return properties;
  }

  /**
   * Method for storing the properties in a file.
   * @param file the property file.
   * @param a vector of properties.
   * @throws java.lang.Exception if there is any problem with storing.
   */
  public synchronized static void store(java.lang.String file, java.util.Vector<Property> properties) throws java.lang.Exception
  {
    java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(file), elv.util.Util.FILE_ENCODING));
    for(Property iteratorProperty : properties)
    {
      fileWriter.println(iteratorProperty.toLine());
    }
    fileWriter.close();
  }
  
  /**
   * Method for getting the icon.
   * @return the icon of properties.
   */
  public static javax.swing.ImageIcon getIcon()
  {
    return new javax.swing.ImageIcon(new Util().getClass().getResource("/resources/images/properties.gif"));
  }
  
  /**
   * Method for getting the title.
   * @return the title of properties.
   */
  public static java.lang.String getTitle()
  {
    return Util.translate(PROPERTIES_NAME);
  }
  
}
