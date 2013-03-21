/*
 * ClusterDocumenting.java
 */
package elv.task.executables;

/**
 * Class for documenting a cluster analysis.
 * @author Qpa
 */
public class ClusterDocumenting extends Documenting
{
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public ClusterDocumenting() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @param execution an execution object.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public boolean execute(elv.task.Execution execution) throws java.lang.Exception
  {
    execution.setExecutable(this);
    // Initialize.
    java.util.Vector<java.util.Locale> locales = (java.util.Vector<java.util.Locale>)elv.util.Property.get(LOCALES_NAME, properties).getValue();
    // Determine mean year for population.
    int meanYearCount = 1;
    int firstYear = execution.getYears().get(0);
    int lastYear = execution.getYears().get(execution.getYears().size() - 1);
    int meanYear = (firstYear + lastYear) / 2;
    if(meanYear - firstYear < lastYear - meanYear)
    {
      meanYearCount = 2;
    }
    
    com.lowagie.text.pdf.BaseFont baseFont = com.lowagie.text.pdf.BaseFont.createFont(com.lowagie.text.pdf.BaseFont.HELVETICA, com.lowagie.text.pdf.BaseFont.CP1250, com.lowagie.text.pdf.BaseFont.NOT_EMBEDDED);
    com.lowagie.text.Font titleFont = new com.lowagie.text.Font(baseFont, 26, com.lowagie.text.Font.BOLD, new java.awt.Color(0, 64, 128));
    com.lowagie.text.Font subTitleFont = new com.lowagie.text.Font(baseFont, 14, com.lowagie.text.Font.NORMAL, new java.awt.Color(0, 0, 0));
    com.lowagie.text.Font chapterFont = new com.lowagie.text.Font(baseFont, 20, com.lowagie.text.Font.UNDERLINE, new java.awt.Color(128, 0, 0));
    com.lowagie.text.Font sectionFont = new com.lowagie.text.Font(baseFont, 14, com.lowagie.text.Font.BOLD, new java.awt.Color(0, 0, 128));
    com.lowagie.text.Font subSectionFont = new com.lowagie.text.Font(baseFont, 12, com.lowagie.text.Font.BOLD, new java.awt.Color(0, 0, 0));
    com.lowagie.text.Font textFont = new com.lowagie.text.Font(baseFont, 9, com.lowagie.text.Font.NORMAL, new java.awt.Color(0, 0, 0));
    com.lowagie.text.Font boldTextFont = new com.lowagie.text.Font(baseFont, 9, com.lowagie.text.Font.BOLD, new java.awt.Color(0, 0, 0));
    com.lowagie.text.Chunk listSymbol = new com.lowagie.text.Chunk("\u2022", textFont);

    // Parse.
    execution.getProgresses().push(new elv.util.Progress(LOCALES_NAME, 0, locales.size()));
    for(int localeCount = 0; localeCount < locales.size(); localeCount++)
    {
      java.util.Locale iteratorLocale = locales.get(localeCount);
      
      com.lowagie.text.Document document = new com.lowagie.text.Document();
      java.lang.String pathName = execution.getTask().getExecutionFolderPath() + elv.util.Util.getFS() + getExecutionFiles().get(localeCount);
      com.lowagie.text.pdf.PdfWriter pdfWriter = com.lowagie.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(pathName));
      document.open();
      
      document.add(new com.lowagie.text.Paragraph("\n\n\n\n\n", titleFont));
// FAKE: TIME/SPACE
      boolean isTimeSpace = false;
      java.lang.String titleEntry = (isTimeSpace ? "Documenting.Cluster.Title.Space" : "Documenting.Cluster.Title.TimeSpace");
      com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph(elv.util.Util.translate(titleEntry, iteratorLocale), titleFont);
      title.setAlignment(com.lowagie.text.Paragraph.ALIGN_CENTER);
      document.add(title);
      com.lowagie.text.Paragraph subTitle = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.SubTitle", iteratorLocale), subTitleFont);
      subTitle.setAlignment(com.lowagie.text.Paragraph.ALIGN_CENTER);
      document.add(subTitle);
      document.resetPageCount();
      com.lowagie.text.HeaderFooter header = new com.lowagie.text.HeaderFooter(new com.lowagie.text.Phrase("OKK-OKI", textFont), false);
      header.setAlignment(com.lowagie.text.HeaderFooter.ALIGN_RIGHT);
      header.setBorder(com.lowagie.text.Rectangle.BOTTOM);
      document.setHeader(header);
      com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(new com.lowagie.text.Phrase("", textFont), true);
      footer.setAlignment(com.lowagie.text.HeaderFooter.ALIGN_RIGHT);
      footer.setBorder(com.lowagie.text.Rectangle.TOP);
      document.setFooter(footer);
      document.newPage();

      int chapterCount = 0;

      // Chapter 1.
      com.lowagie.text.Paragraph chapterTitle = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter1", iteratorLocale), chapterFont);
      com.lowagie.text.Chapter chapter = new com.lowagie.text.Chapter(chapterTitle, ++chapterCount);
      chapter.setBookmarkOpen(false);
      // Chapter 1, Section 1.
      com.lowagie.text.Section section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1", iteratorLocale), sectionFont), 2);
      java.lang.String paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph1", iteratorLocale).replaceFirst("%0", execution.getTask().getName());
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      java.lang.Object value = elv.util.Property.get(elv.task.Task.DESCRIPTION_NAME, execution.getTask().getProperties()).getValue();
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", value.toString());
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(firstYear)).replaceFirst("%1", elv.util.Util.format(lastYear));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      SpotStandardization spotStandardization = null;
      for(elv.task.executables.Executable iteratorExecutable : execution.getTask().getExecutables())
      {
        if(iteratorExecutable instanceof SpotStandardization)
        {
          spotStandardization = (SpotStandardization)iteratorExecutable;
          break;
        }
      }
      value = elv.util.Property.get(SpotStandardization.SIGNIFICANCE_NAME, spotStandardization.getProperties()).getValue();
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph4", iteratorLocale).replaceFirst("%0", elv.util.Util.translate(value.toString()));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      double significanceLevel = (java.lang.Double)elv.util.Property.get(SpotStandardization.SIGNIFICANCE_LEVEL_NAME, spotStandardization.getProperties()).getValue();
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph5", iteratorLocale).replaceFirst("%0", elv.util.Util.format(significanceLevel, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      java.lang.String line = "";
      for(int i = 0; i < execution.getDiseases().size(); i++)
      {
        elv.util.parameters.DiseaseDiagnosis iteratorDisease = execution.getDiseases().get(i);
        int level = 0;
        if(i == 0)
        {
          level = iteratorDisease.getParagraphLevel();
          line += iteratorDisease.getCodes()[0];
        }
        else if(iteratorDisease.getParagraphLevel() == level)
        {
          line += ", " + iteratorDisease.getCodes()[0];
        }
      }
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph6", iteratorLocale).replaceFirst("%0", line);
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      int totalPopulation = 0;
      int totalCases = 0;
      double totalArea = 0;
      for(elv.util.parameters.District iteratorDistrict : execution.getDistricts())
      {
        totalPopulation += iteratorDistrict.getResult().population;
        totalCases += iteratorDistrict.getResult().observedCases;
        totalArea += iteratorDistrict.getKm2Area();
      }
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph7", iteratorLocale).replaceFirst("%0", elv.util.Util.format(totalPopulation, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph8", iteratorLocale).replaceFirst("%0", elv.util.Util.format(totalCases, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section1.Paragraph9", iteratorLocale);
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      // Chapter 1, Section 2.
      section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2", iteratorLocale), sectionFont), 2);
      // Chapter 1, Section 2, SubSection 1.
      com.lowagie.text.Section subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section1", iteratorLocale), subSectionFont), 3);
      int count = 0;
      int population = 0;
      double area = 0;
      int superCount = 0;
      int superPopulation = 0;
      double superArea = 0;
      for(elv.util.parameters.Spot iteratorSpot : execution.getSpots())
      {
        if(iteratorSpot.getCode() != elv.util.parameters.Spot.UNFAVORABLE_CODE && iteratorSpot.getCode() != elv.util.parameters.Spot.FAVORABLE_CODE)
        {
// FAKE : NORMAL/SUPER
          if(true) // Normal cluster.
          {
            count++;
            population += iteratorSpot.getResult().population;
            area += iteratorSpot.getArea();
          }
          else // Super cluster.
          {
            superCount++;
            superPopulation += iteratorSpot.getResult().population;
            superArea += iteratorSpot.getArea();
          }
        }
      }
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section1.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(count, iteratorLocale));
      subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(population, iteratorLocale));
      subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(area, iteratorLocale));
      subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      // Chapter 1, Section 2, SubSection 2.
      subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section2", iteratorLocale), subSectionFont), 3);
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section2.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(superCount, iteratorLocale));
      subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section2.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(superPopulation, iteratorLocale));
      subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter1.Section2.Section2.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(superArea, iteratorLocale));
      subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      document.add(chapter);
      
      // Chapter 2.
      chapterTitle = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter2", iteratorLocale), chapterFont);
      chapter = new com.lowagie.text.Chapter(chapterTitle, ++chapterCount);
      chapter.setBookmarkOpen(false);
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Spot.AGGREGATE_TITLE, 0, 2));
      for(int iteratorType = 0; iteratorType < 2; iteratorType++)
      {
        int selectedCount = (iteratorType == 0 ? count : superCount);
        if(selectedCount > 0)
        {
          boolean isSuper = (iteratorType == 0 ? false : true);
          elv.util.parameters.Spot spot = (iteratorType == 0 ? execution.getSpots().get(execution.getSpots().size() - 2) : execution.getSpots().get(execution.getSpots().size() - 1));

          // Chapter 2, Section 1-2.
          titleEntry = (iteratorType == 0 ? "Documenting.Cluster.Chapter2.Section1" : "Documenting.Cluster.Chapter2.Section2");
          section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate(titleEntry, iteratorLocale), sectionFont), 2);
          // Chapter 2, Section 1-2, SubSection 1.
          subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1", iteratorLocale), subSectionFont), 3);
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getResult().observedCases, iteratorLocale)).replaceFirst("%1", elv.util.Util.format((double)spot.getResult().observedCases * 100 / totalCases, iteratorLocale));
          subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getResult().expectedCases, iteratorLocale));
          subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getResult().smr, iteratorLocale));
          subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph4", iteratorLocale);
          subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          com.lowagie.text.List list = new com.lowagie.text.List(false, 10);
          list.setListSymbol(listSymbol);
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph4.List1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(firstYear));
          list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
          double startIncidence = 0;
          for(SpotYearResult iteratorYearResult : spot.getYearResults())
          {
            if(iteratorYearResult.year == firstYear)
            {
              startIncidence = iteratorYearResult.incidence;
              break;
            }
          }
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph4.List2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(startIncidence, iteratorLocale));
          list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph4.List3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getResult().trend, iteratorLocale));
          list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section1.Paragraph4.List4", iteratorLocale).replaceFirst("%0", spot.getResult().trendSignificance);
          list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
          subSection.add(list);
          // Chapter 2, Section 1-2, Section 2.
          subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section2", iteratorLocale), subSectionFont), 3);
          double trend = spot.getResult().trend;
          double trendConstant = spot.getResult().trendConstant;
          double[] trendIncidences = new double[execution.getYears().size()];
          double[] effectiveIncidences = new double[execution.getYears().size()];
          double minIncidence = java.lang.Double.MAX_VALUE;
          double maxIncidence = 0;
          for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
          {
            SpotYearResult iteratorYearResult = spot.getYearResults().get(yearCount);
            trendIncidences[yearCount] = trendConstant + trend * iteratorYearResult.year;
            effectiveIncidences[yearCount] = iteratorYearResult.incidence;
            if(minIncidence >  trendIncidences[yearCount])
            {
              minIncidence = trendIncidences[yearCount];
            }
            if(minIncidence >  effectiveIncidences[yearCount])
            {
              minIncidence = effectiveIncidences[yearCount];
            }
            if(maxIncidence <  trendIncidences[yearCount])
            {
              maxIncidence = trendIncidences[yearCount];
            }
            if(maxIncidence <  effectiveIncidences[yearCount])
            {
              maxIncidence = effectiveIncidences[yearCount];
            }
          }
          int width = 450;
          int height = 450;
          int leftMargin = 50;
          int bottomMargin = 50;
          int topMargin = 10;
          int rightMargin = 10;
          int rulerLineLength = 4;
          java.awt.Color baseColor = new java.awt.Color(0, 0, 0);
          java.awt.Color trendColor = new java.awt.Color(128, 0, 0);
          java.awt.Color incidenceColor = new java.awt.Color(0, 0, 128);
          com.lowagie.text.pdf.PdfTemplate template = pdfWriter.getDirectContent().createTemplate(width, height);
          template.moveTo(leftMargin, height - topMargin);
          template.lineTo(leftMargin, bottomMargin);
          template.lineTo(width - rightMargin, bottomMargin);
          template.setColorStroke(baseColor);
          template.stroke();
          float pointRadius = 2;
          float xOffset = (width - leftMargin - rightMargin) / execution.getYears().size();
          float yOffset = (height - bottomMargin - topMargin) / execution.getYears().size();
          float yAxisLength = (height - bottomMargin - topMargin) - yOffset;
          float previousX = 0;
          float previousTrendY = 0; 
          float previousIncidenceY = 0;
          for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
          {
            // Rulers.
            template.moveTo(leftMargin + (yearCount + 1) * xOffset, bottomMargin);
            template.lineTo(leftMargin + (yearCount + 1) * xOffset, bottomMargin - rulerLineLength);
            template.moveTo(leftMargin, bottomMargin + (yearCount + 1) * yOffset);
            template.lineTo(leftMargin - rulerLineLength, bottomMargin + (yearCount + 1) * yOffset);
            template.setColorStroke(baseColor);
            template.stroke();
            // Points.
            float x = leftMargin + (yearCount + 1) * xOffset;
            float trendY = bottomMargin + yOffset + (float)(trendIncidences[yearCount] - minIncidence)  / (float)(maxIncidence - minIncidence) * yAxisLength;
            template.circle(x, trendY, pointRadius);
            template.setColorFill(trendColor);
            template.fill();
            float incidenceY = bottomMargin + yOffset + (float)(effectiveIncidences[yearCount] - minIncidence)  / (float)(maxIncidence - minIncidence) * yAxisLength;
            template.circle(x, incidenceY, pointRadius);
            template.setColorFill(incidenceColor);
            template.fill();
            // Lines.
            if(yearCount > 0)
            {
              template.moveTo(previousX, previousTrendY);
              template.lineTo(x, trendY);
              template.setColorStroke(trendColor);
              template.stroke();
              template.moveTo(previousX, previousIncidenceY);
              template.lineTo(x, incidenceY);
              template.setColorStroke(incidenceColor);
              template.stroke();
            }
            previousX = x;
            previousTrendY = trendY;
            previousIncidenceY = incidenceY;
          }
          template.setColorStroke(new java.awt.Color(0, 0, 0));
          template.setColorFill(new java.awt.Color(0, 0, 0));
          template.beginText();
          float fontSize = textFont.getCalculatedSize();
          template.setFontAndSize(baseFont, fontSize);
          template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.translate("Documenting.Header.Incidence", iteratorLocale), fontSize, bottomMargin + (height - bottomMargin - topMargin) / 2, 90);
          template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.translate("Documenting.Header.Years", iteratorLocale), leftMargin + (width - leftMargin - rightMargin) / 2, fontSize, 0);
          int textLineGap = 2;
          double incidenceOffset = (maxIncidence - minIncidence) /execution.getYears().size();
          for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
          {
            template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_RIGHT, elv.util.Util.format(execution.getYears().get(yearCount), iteratorLocale), leftMargin + (yearCount + 1) * xOffset + fontSize / 2, bottomMargin - rulerLineLength - textLineGap, 90);
            template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_RIGHT, elv.util.Util.format(minIncidence + yearCount * incidenceOffset, iteratorLocale), leftMargin - rulerLineLength - textLineGap, bottomMargin + (yearCount + 1) * yOffset - fontSize / 2, 0);
          }
          template.endText();
          com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(template);
          com.lowagie.text.pdf.PdfPTable trendTable = new com.lowagie.text.pdf.PdfPTable(1);
          trendTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
          trendTable.setSpacingBefore(10);
          trendTable.setWidthPercentage(100);
          com.lowagie.text.pdf.PdfPCell cell = new com.lowagie.text.pdf.PdfPCell(image);
          cell.setBorderWidth(0);
          trendTable.addCell(cell);
          subSection.add(trendTable);

          com.lowagie.text.pdf.PdfPTable areaTable = new com.lowagie.text.pdf.PdfPTable(4);
          areaTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
          areaTable.setSpacingBefore(10);
          areaTable.setWidthPercentage(75);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Region", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          areaTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Area", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          areaTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Settlements", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          areaTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SmallBig", iteratorLocale).replaceFirst("%0", elv.util.Util.format(SMALL_BIG_LEVEL, iteratorLocale)), textFont));
          cell.setBorderWidth(2);
          areaTable.addCell(cell);
          areaTable.setHeaderRows(1);
          com.lowagie.text.pdf.PdfPTable populationTable = new com.lowagie.text.pdf.PdfPTable(4);
          populationTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
          populationTable.setSpacingBefore(10);
          populationTable.setWidthPercentage(75);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Region", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          populationTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Population", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          populationTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Population.Percent", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          populationTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR.Mean", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          populationTable.addCell(cell);
          populationTable.setHeaderRows(1);
          double minSMR = java.lang.Double.MAX_VALUE;
          double maxSMR = 0;
          java.util.Vector<elv.util.parameters.Spot> populationRanks = new java.util.Vector<elv.util.parameters.Spot>();
          java.util.Vector<elv.util.parameters.Spot> meanSMRRanks = new java.util.Vector<elv.util.parameters.Spot>();
          java.util.Vector<elv.util.parameters.Spot> weighedIndexRanks = new java.util.Vector<elv.util.parameters.Spot>();
          java.util.Vector<elv.util.parameters.Spot> areaRanks = new java.util.Vector<elv.util.parameters.Spot>();
          for(int spotCount = 0; spotCount < execution.getSpots().size(); spotCount++)
          {
            elv.util.parameters.Spot iteratorSpot = execution.getSpots().get(spotCount);
            if(iteratorSpot.getResult().isSelected && iteratorSpot.getResult().isSuper == isSuper &&
               iteratorSpot.getCode() != elv.util.parameters.Spot.UNFAVORABLE_CODE && iteratorSpot.getCode() != elv.util.parameters.Spot.FAVORABLE_CODE)
            {
              int settlementCount = 0;
              int smallSettlementCount = 0;
              int bigSettlementCount = 0;
              for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
              {
                for(elv.util.parameters.Settlement iteratorSettlement : execution.getBaseSettlements())
                {
                  if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() && iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()) &&
                     iteratorSettlement.getYearResults().size() > 0)
                  {
                    settlementCount++;
                    int settlementPopulation = 0;
                    for(SettlementYearResult iteratorYearResult : iteratorSettlement.getYearResults())
                    {
                      if(iteratorYearResult.year == meanYear || iteratorYearResult.year == (meanYear + meanYearCount - 1))
                      {
                        settlementPopulation += iteratorYearResult.population / meanYearCount;
                      }
                    }
                    if(settlementPopulation < SMALL_BIG_LEVEL)
                    {
                      smallSettlementCount++;
                    }
                    else
                    {
                      bigSettlementCount++;
                    }
                  }
                }
              }
              int insertIndex = 0;
              for(int i = 0; i < populationRanks.size(); i++)
              {
                insertIndex++;
                if(iteratorSpot.getResult().population < populationRanks.get(i).getResult().population)
                {
                  break;
                }
              }
              populationRanks.add(insertIndex, iteratorSpot);
              
              insertIndex = 0;
              for(int i = 0; i < meanSMRRanks.size(); i++)
              {
                insertIndex++;
                if(iteratorSpot.getResult().meanSMR < meanSMRRanks.get(i).getResult().meanSMR)
                {
                  break;
                }
              }
              meanSMRRanks.add(insertIndex, iteratorSpot);
              
              insertIndex = 0;
              for(int i = 0; i < weighedIndexRanks.size(); i++)
              {
                insertIndex++;
                if(iteratorSpot.getResult().meanSMR * iteratorSpot.getResult().population < weighedIndexRanks.get(i).getResult().meanSMR * weighedIndexRanks.get(i).getResult().population)
                {
                  break;
                }
              }
              weighedIndexRanks.add(insertIndex, iteratorSpot);
              
              insertIndex = 0;
              for(int i = 0; i < areaRanks.size(); i++)
              {
                insertIndex++;
                if(iteratorSpot.getKm2Area() < areaRanks.get(i).getKm2Area())
                {
                  break;
                }
              }
              areaRanks.add(insertIndex, iteratorSpot);
              
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorSpot.getCode(), iteratorLocale), textFont));
              areaTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorSpot.getKm2Area(), iteratorLocale), textFont));
              areaTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(settlementCount, iteratorLocale), textFont));
              areaTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(bigSettlementCount == 0 ? 0 : (double)smallSettlementCount / bigSettlementCount, iteratorLocale), textFont));
              areaTable.addCell(cell);

              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorSpot.getCode(), iteratorLocale), textFont));
              populationTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorSpot.getResult().population, iteratorLocale), textFont));
              populationTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format((double)iteratorSpot.getResult().population * 100 / totalPopulation, iteratorLocale), textFont));
              populationTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorSpot.getResult().smr, iteratorLocale), textFont));
              populationTable.addCell(cell);
            }
          }
          // Chapter 2, Section 1-2, Section 3.
          subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section3", iteratorLocale), subSectionFont), 3);
          subSection.add(areaTable);
          // Chapter 2, Section 1-2, Section 4.
          subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section4", iteratorLocale), subSectionFont), 3);
          subSection.add(populationTable);
          // Chapter 2, Section 1-2, Section 5.
          subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section5", iteratorLocale), subSectionFont), 3);
          paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter2.Section1-2.Section5.Paragraph1", iteratorLocale);
          subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          com.lowagie.text.pdf.PdfPTable rankTable = new com.lowagie.text.pdf.PdfPTable(5);
          rankTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
          rankTable.setSpacingBefore(10);
          rankTable.setWidthPercentage(100);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Rank", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          rankTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Population", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          rankTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR.Mean", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          rankTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.WeighedIndex", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          rankTable.addCell(cell);
          cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Area", iteratorLocale), textFont));
          cell.setBorderWidth(2);
          rankTable.addCell(cell);
          rankTable.setHeaderRows(1);
          rankTable.getDefaultCell().setBorderWidth(1);
          for(int i = 0; i < populationRanks.size(); i++)
          {
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(i + 1, iteratorLocale), textFont));
            rankTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(populationRanks.get(i).getCode(), iteratorLocale), textFont));
            rankTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(meanSMRRanks.get(i).getCode(), iteratorLocale), textFont));
            rankTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(weighedIndexRanks.get(i).getCode(), iteratorLocale), textFont));
            rankTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(areaRanks.get(i).getCode(), iteratorLocale), textFont));
            rankTable.addCell(cell);
          }
          subSection.add(rankTable);
          document.add(chapter);
        }
        execution.getProgresses().peek().setValue(iteratorType + 1);
      }
      execution.getProgresses().pop();
      
      // Chapter 3.
      chapterTitle = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter3", iteratorLocale), chapterFont);
      chapter = new com.lowagie.text.Chapter(chapterTitle, ++chapterCount);
      chapter.setBookmarkOpen(false);
      if(count > 0)
      {
        // Chapter 3, Section1.
        section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1", iteratorLocale), sectionFont), 2);
        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Spot.TITLE, 0, execution.getSpots().size()));
        for(int spotCount = 0; spotCount < execution.getSpots().size(); spotCount++)
        {
          elv.util.parameters.Spot iteratorSpot = execution.getSpots().get(spotCount);
          if(iteratorSpot.getResult().isSelected && !iteratorSpot.getResult().isSuper &&
             iteratorSpot.getCode() != elv.util.parameters.Spot.UNFAVORABLE_CODE && iteratorSpot.getCode() != elv.util.parameters.Spot.FAVORABLE_CODE)
          {
            // Chapter 3, Section1, Section.
            if(chapter.getChunks().size() > 0)
            {
              chapter.add(new com.lowagie.text.Paragraph(com.lowagie.text.Chunk.NEXTPAGE));
              chapter.add(new com.lowagie.text.Paragraph("\n\n", sectionFont));
            }
            subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section", iteratorLocale) + iteratorSpot.getCode(), sectionFont), 2);
            // Chapter 3, Section 1, Subsection, Section 1.
            com.lowagie.text.Section subSubSection = subSection.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section1", iteratorLocale), subSectionFont), 3);
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section1.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getKm2Area(), iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().population, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section1.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().observedCases, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section1.Paragraph4", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().expectedCases, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section1.Paragraph5", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().smr, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section1.Paragraph6", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().smrSignificance, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            // Chapter 3, Section 1, Subsection, Section 2.
            subSubSection = subSection.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section2", iteratorLocale), subSectionFont), 3);
            com.lowagie.text.pdf.PdfPTable settlementTable = new com.lowagie.text.pdf.PdfPTable(8);
            settlementTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
            settlementTable.setSpacingBefore(10);
            settlementTable.setWidths(new int[]{30, 10, 10, 7, 15, 10, 10, 15});
            settlementTable.setWidthPercentage(100);
            com.lowagie.text.pdf.PdfPCell cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Name", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Population.Layer", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Population.Total", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.District", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR.Significance", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Cases.Observed", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Cases.Expected", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            settlementTable.setHeaderRows(1);
            int totalSettlementCount = 0;
            int lowSettlementCount = 0;
            int highSettlementCount = 0;
            for(int settlementCount = 0; settlementCount < execution.getBaseSettlements().size(); settlementCount++)
            {
              elv.util.parameters.Settlement iteratorSettlement = execution.getBaseSettlements().get(settlementCount);
              for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
              {
                if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() && iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()) &&
                   iteratorSettlement.getYearResults().size() > 0)
                {
                  totalSettlementCount++;
                  int settlementPopulation = 0;
                  for(SettlementYearResult iteratorYearResult : iteratorSettlement.getYearResults())
                  {
                    if(iteratorYearResult.year == meanYear || iteratorYearResult.year == (meanYear + meanYearCount - 1))
                    {
                      settlementPopulation += iteratorYearResult.population / meanYearCount;
                    }
                  }
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorSettlement.getName(), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(settlementPopulation, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().population, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getCode(), iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().observedCases, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().expectedCases, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smr, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smrSignificance, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  if(iteratorDistrict.getResult().smr < 1)
                  {
                    lowSettlementCount++;
                  }
                  else if(iteratorDistrict.getResult().smr > 1)
                  {
                    highSettlementCount++;
                  }
                  break;
                }
              }
            }
            subSubSection.add(settlementTable);
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section2.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(lowSettlementCount, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter3.Section1.Section.Section2.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(highSettlementCount, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          }
          execution.getProgresses().peek().setValue(spotCount + 1);
        }
        execution.getProgresses().pop();
        document.add(chapter);
      }
      
      // Chapter 4.
      chapterTitle = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter4", iteratorLocale), chapterFont);
      chapter = new com.lowagie.text.Chapter(chapterTitle, ++chapterCount);
      chapter.setBookmarkOpen(false);
      if(superCount > 0)
      {
        // Chapter 4, Section1.
        section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1", iteratorLocale), sectionFont), 2);
        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Spot.TITLE, 0, execution.getSpots().size()));
        for(int spotCount = 0; spotCount < execution.getSpots().size(); spotCount++)
        {
          elv.util.parameters.Spot iteratorSpot = execution.getSpots().get(spotCount);
          if(iteratorSpot.getResult().isSelected && iteratorSpot.getResult().isSuper &&
             iteratorSpot.getCode() != elv.util.parameters.Spot.UNFAVORABLE_CODE && iteratorSpot.getCode() != elv.util.parameters.Spot.FAVORABLE_CODE)
          {
            // Chapter 4, Section1, Section.
            if(chapter.getChunks().size() > 0)
            {
              chapter.add(new com.lowagie.text.Paragraph(com.lowagie.text.Chunk.NEXTPAGE));
              chapter.add(new com.lowagie.text.Paragraph("\n\n", sectionFont));
            }
            subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section", iteratorLocale) + iteratorSpot.getCode(), sectionFont), 2);
            // Chapter 4, Section 1, Subsection, Section 1.
            com.lowagie.text.Section subSubSection = subSection.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section1", iteratorLocale), subSectionFont), 3);
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section1.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getKm2Area(), iteratorLocale)).replaceFirst("%1", elv.util.Util.format(iteratorSpot.getKm2Area() * 100 / totalArea, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().population, iteratorLocale)).replaceFirst("%1", elv.util.Util.format(iteratorSpot.getResult().population * 100 / totalPopulation, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section1.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().observedCases, iteratorLocale)).replaceFirst("%1", elv.util.Util.format(iteratorSpot.getResult().observedCases * 100 / totalCases, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section1.Paragraph4", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().expectedCases, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section1.Paragraph5", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().smr, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section1.Paragraph6", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().smrSignificance, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            // Chapter 4, Section 1, Subsection, Section 2.
            subSubSection = subSection.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section2", iteratorLocale), subSectionFont), 3);
            com.lowagie.text.pdf.PdfPTable settlementTable = new com.lowagie.text.pdf.PdfPTable(8);
            settlementTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
            settlementTable.setSpacingBefore(10);
            settlementTable.setWidths(new int[]{30, 10, 10, 7, 15, 10, 10, 15});
            settlementTable.setWidthPercentage(100);
            com.lowagie.text.pdf.PdfPCell cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Code", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Counties", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Area", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Population", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Cases.Observed", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Cases.Expected", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR.Significance", iteratorLocale), textFont));
            cell.setBorderWidth(2);
            settlementTable.addCell(cell);
            settlementTable.setHeaderRows(1);
// FAKE: iteratorCluster instead of iteratorDistrict.
            elv.util.parameters.Settlement county = null;
            int countyCount = 0;
            int totalSettlementCount = 0;
            int countySettlementCount = 0;
            int lowSettlementCount = 0;
            int highSettlementCount = 0;
            boolean countableCounty = false;
            for(int settlementCount = 0; settlementCount < execution.getBaseSettlements().size(); settlementCount++)
            {
              elv.util.parameters.Settlement iteratorSettlement = execution.getBaseSettlements().get(settlementCount);
              elv.util.parameters.Settlement nextSettlement = null;
              if(settlementCount < execution.getBaseSettlements().size() - 1)
              {
                nextSettlement = execution.getBaseSettlements().get(settlementCount + 1);
              }
              if(!iteratorSettlement.isRealSettlement() && !iteratorSettlement.isPlace() &&
                 nextSettlement != null && nextSettlement.isPlace())
              {
                if(county != null && countableCounty)
                {
                  countyCount++;
                  countySettlementCount = 0;
                  countableCounty = false;
                }
                county = iteratorSettlement;
              }
              for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
              {
                if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() && iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()) &&
                   iteratorSettlement.getYearResults().size() > 0)
                {
                  if(iteratorSettlement.getParagraph().contains(county.getParagraph()))
                  {
                    countableCounty = true;
                  }
                  totalSettlementCount++;
                  countySettlementCount++;
                  int settlementPopulation = 0;
                  for(SettlementYearResult iteratorYearResult : iteratorSettlement.getYearResults())
                  {
                    if(iteratorYearResult.year == meanYear || iteratorYearResult.year == (meanYear + meanYearCount - 1))
                    {
                      settlementPopulation += iteratorYearResult.population / meanYearCount;
                    }
                  }
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getCode(), iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(countyCount, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getKm2Area(), iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().population, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().observedCases, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().expectedCases, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smr, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smrSignificance, iteratorLocale), textFont));
                  settlementTable.addCell(cell);
                  if(iteratorDistrict.getResult().smr < 1)
                  {
                    lowSettlementCount++;
                  }
                  else if(iteratorDistrict.getResult().smr > 1)
                  {
                    highSettlementCount++;
                  }
                  break;
                }
              }
            }
            if(county != null && countableCounty)
            {
              countyCount++;
            }
            subSubSection.add(settlementTable);
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section2.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(lowSettlementCount, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
            paragraphLine = elv.util.Util.translate("Documenting.Cluster.Chapter4.Section1.Section.Section2.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(highSettlementCount, iteratorLocale));
            subSubSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          }
          execution.getProgresses().peek().setValue(spotCount + 1);
        }
        execution.getProgresses().pop();
        document.add(chapter);
      }
      
      
      document.close();
      execution.getProgresses().peek().setValue(localeCount + 1);
    }
    execution.getProgresses().pop();
    setDone(execution.getTask(), true);
    return true;
  }
  
}
