/*
 * TwoFactorsDocumenting.java
 */
package elv.task.executables;

/**
 * Class for documenting a two-factors analysis.
 * @author Qpa
 */
public class TwoFactorsDocumenting extends Documenting
{
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public TwoFactorsDocumenting() throws java.lang.Exception
  {
    super();
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
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
      com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Title", iteratorLocale), titleFont);
      title.setAlignment(com.lowagie.text.Paragraph.ALIGN_CENTER);
      document.add(title);
      com.lowagie.text.Paragraph subTitle = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.SubTitle", iteratorLocale), subTitleFont);
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
      com.lowagie.text.Paragraph chapterTitle = new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter1", iteratorLocale), chapterFont);
      com.lowagie.text.Chapter chapter = new com.lowagie.text.Chapter(chapterTitle, ++chapterCount);
      chapter.setBookmarkOpen(false);
      // Chapter 1, Section 1.
      com.lowagie.text.Section section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter1.Section1", iteratorLocale), sectionFont), 2);
      java.lang.String paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section1.Paragraph1", iteratorLocale).replaceFirst("%0", execution.getTask().getName());
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      java.lang.Object value = elv.util.Property.get(elv.task.Task.DESCRIPTION_NAME, execution.getTask().getProperties()).getValue();
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", value.toString());
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section1.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(firstYear)).replaceFirst("%1", elv.util.Util.format(lastYear));
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
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section1.Paragraph4", iteratorLocale).replaceFirst("%0", line);
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      // Chapter 1, Section 2.
      section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter1.Section2", iteratorLocale), sectionFont), 2);
      int favorableCount = 0;
      int selectedFavorableCount = 0;
      int unfavorableCount = 0;
      int selectedUnfavorableCount = 0;
      for(elv.util.parameters.Spot iteratorSpot : execution.getSpots())
      {
        if(iteratorSpot.getCode() != elv.util.parameters.Spot.UNFAVORABLE_CODE && iteratorSpot.getCode() != elv.util.parameters.Spot.FAVORABLE_CODE)
        {
          if(iteratorSpot.getResult().isFavorable)
          {
            favorableCount++;
            if(iteratorSpot.getResult().isSelected)
            {
              selectedFavorableCount++;
            }
          }
          else
          {
            unfavorableCount++;
            if(iteratorSpot.getResult().isSelected)
            {
              selectedUnfavorableCount++;
            }
          }
        }
      }
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section2.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(unfavorableCount, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section2.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(favorableCount, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      RegionSpotting regionSpotting = null;
      for(elv.task.executables.Executable iteratorExecutable : execution.getTask().getExecutables())
      {
        if(iteratorExecutable instanceof RegionSpotting)
        {
          regionSpotting = (RegionSpotting)iteratorExecutable;
          break;
        }
      }
      value = elv.util.Property.get(RegionSpotting.PERCENTAGE_NAME, regionSpotting.getProperties()).getValue();
      double percentage = (java.lang.Double)value;
      int totalCases = 0;
      int totalPopulation = 0;
      double totalArea = 0;
      for(elv.util.parameters.District iteratorDistrict : execution.getDistricts())
      {
        totalCases += iteratorDistrict.getResult().observedCases;
        totalPopulation += iteratorDistrict.getResult().population;
        totalArea += iteratorDistrict.getKm2Area();
      }
      int selectionPopulation = (int)((double)totalPopulation * percentage / 100);
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section2.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(selectionPopulation, iteratorLocale)).replaceFirst("%1", elv.util.Util.format(percentage, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section2.Paragraph4", iteratorLocale);
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      com.lowagie.text.List list = new com.lowagie.text.List(false, 10);
      list.setListSymbol(listSymbol);
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section2.Paragraph4.List1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(selectedUnfavorableCount, iteratorLocale));
      list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section2.Paragraph4.List2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(selectedFavorableCount, iteratorLocale));
      list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
      section.add(list);
      // Chapter 1, Section 3.
      section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter1.Section3", iteratorLocale), sectionFont), 2);
      double quotient = (selectedFavorableCount == 0 ? 0 :(double)execution.getSpots().get(execution.getSpots().size() - 2).getResult().population / execution.getSpots().get(execution.getSpots().size() - 1).getResult().population);
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section3.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(quotient, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      quotient = (selectedFavorableCount == 0 ? 0 :execution.getSpots().get(execution.getSpots().size() - 2).getKm2Area() / execution.getSpots().get(execution.getSpots().size() - 1).getKm2Area());
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section3.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(quotient, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      quotient = (selectedFavorableCount == 0 ? 0 :execution.getSpots().get(execution.getSpots().size() - 2).getResult().smr / execution.getSpots().get(execution.getSpots().size() - 1).getResult().smr);
      paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter1.Section3.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(quotient, iteratorLocale));
      section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
      document.add(chapter);

      // Chapter 2-3.
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Spot.AGGREGATE_TITLE, 0, 2));
      for(int iteratorFavorable = 0; iteratorFavorable < 2; iteratorFavorable++)
      {
        int selectedCount = (iteratorFavorable == 0 ? selectedUnfavorableCount : selectedFavorableCount);
        if(selectedCount > 0)
        {
          paragraphLine = (iteratorFavorable == 0 ? elv.util.Util.translate("Documenting.Region.Chapter2", iteratorLocale) : elv.util.Util.translate("Documenting.Region.Chapter3", iteratorLocale));
          chapterTitle = new com.lowagie.text.Paragraph(paragraphLine, chapterFont);
          chapter = new com.lowagie.text.Chapter(chapterTitle, ++chapterCount);
          chapter.setBookmarkOpen(false);

          boolean favorable = (iteratorFavorable == 0 ? false : true);
          elv.util.parameters.Spot spot = (iteratorFavorable == 0 ? execution.getSpots().get(execution.getSpots().size() - 2) : execution.getSpots().get(execution.getSpots().size() - 1));

          // Chapter 2-3, Section 1.
          section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1", iteratorLocale), sectionFont), 2);
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getResult().population, iteratorLocale)).replaceFirst("%1", elv.util.Util.format((double)spot.getResult().population * 100 / totalPopulation, iteratorLocale));
          section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getKm2Area(), iteratorLocale)).replaceFirst("%1", elv.util.Util.format(spot.getKm2Area() * 100 / totalArea, iteratorLocale));
          section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getResult().smr, iteratorLocale));
          section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4", iteratorLocale);
          section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
          list = new com.lowagie.text.List(false, 10);
          list.setListSymbol(listSymbol);
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4.List1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(firstYear));
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
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4.List2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(startIncidence, iteratorLocale));
          list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4.List3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(spot.getResult().trend, iteratorLocale));
          list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4.List4", iteratorLocale).replaceFirst("%0", spot.getResult().trendSignificance);
          list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
          section.add(list);
          // Chapter 2, Section 2.
          section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter2-3.Section2", iteratorLocale), sectionFont), 2);
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
          section.add(trendTable);

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
            if(iteratorSpot.getResult().isSelected && iteratorSpot.getResult().isFavorable == favorable &&
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
          // Chapter 2, Section 3
          section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter2-3.Section3", iteratorLocale), sectionFont), 2);
          section.add(areaTable);
          // Chapter 2, Section 4.
          section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter2-3.Section4", iteratorLocale), sectionFont), 2);
          section.add(populationTable);
          // Chapter 2, Section 5.
          section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter2-3.Section5", iteratorLocale), sectionFont), 2);
          paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section5.Paragraph1", iteratorLocale);
          section.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
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
          section.add(rankTable);
          document.add(chapter);
        }
        execution.getProgresses().peek().setValue(iteratorFavorable + 1);
      }
      execution.getProgresses().pop();
      
      // Chapter 4-5.
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Spot.AGGREGATE_TITLE, 0, 2));
      for(int iteratorFavorable = 0; iteratorFavorable < 2; iteratorFavorable++)
      {
        int selectedCount = (iteratorFavorable == 0 ? selectedUnfavorableCount : selectedFavorableCount);
        if(selectedCount > 0)
        {
          paragraphLine = (iteratorFavorable == 0 ? elv.util.Util.translate("Documenting.Region.Chapter4", iteratorLocale) : elv.util.Util.translate("Documenting.Region.Chapter5", iteratorLocale));
          chapterTitle = new com.lowagie.text.Paragraph(paragraphLine, chapterFont);
          chapter = new com.lowagie.text.Chapter(chapterTitle, ++chapterCount);
          chapter.setBookmarkOpen(false);

          boolean favorable = (iteratorFavorable == 0 ? false : true);

          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Spot.TITLE, 0, execution.getSpots().size()));
          for(int spotCount = 0; spotCount < execution.getSpots().size(); spotCount++)
          {
            elv.util.parameters.Spot iteratorSpot = execution.getSpots().get(spotCount);
            if(iteratorSpot.getResult().isSelected && iteratorSpot.getResult().isFavorable == favorable &&
               iteratorSpot.getCode() != elv.util.parameters.Spot.UNFAVORABLE_CODE && iteratorSpot.getCode() != elv.util.parameters.Spot.FAVORABLE_CODE)
            {
              // Chapter 4-5, Section.
              if(chapter.getChunks().size() > 0)
              {
                chapter.add(new com.lowagie.text.Paragraph(com.lowagie.text.Chunk.NEXTPAGE));
                chapter.add(new com.lowagie.text.Paragraph("\n\n", sectionFont));
              }
              section = chapter.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section", iteratorLocale) + iteratorSpot.getCode(), sectionFont), 2);
              // Chapter 4-5, Section, Subsection 1.
              com.lowagie.text.Section subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section1", iteratorLocale), subSectionFont), 3);
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().smr, iteratorLocale));
              subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section1.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().smrSignificance, iteratorLocale));
              subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4", iteratorLocale);
              subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
              list = new com.lowagie.text.List(false, 10);
              list.setListSymbol(listSymbol);
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4.List3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().trend, iteratorLocale));
              list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter2-3.Section1.Paragraph4.List4", iteratorLocale).replaceFirst("%0", iteratorSpot.getResult().trendSignificance);
              list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section1.Paragraph3.List3", iteratorLocale).replaceFirst("%0", elv.util.Util.format(iteratorSpot.getResult().trendCorrelation, iteratorLocale));
              list.add(new com.lowagie.text.ListItem(paragraphLine, textFont));
              subSection.add(list);
              // Chapter 4-5, Section, Subsection 2.
              subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section2", iteratorLocale), subSectionFont), 3);
              double trend = iteratorSpot.getResult().trend;
              double trendConstant = iteratorSpot.getResult().trendConstant;
              double[] trendIncidences = new double[execution.getYears().size()];
              double[] effectiveIncidences = new double[execution.getYears().size()];
              double minIncidence = java.lang.Double.MAX_VALUE;
              double maxIncidence = 0;
              for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
              {
                SpotYearResult iteratorYearResult = iteratorSpot.getYearResults().get(yearCount);
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

              double minSMR = java.lang.Double.MAX_VALUE;
              double maxSMR = 0;
              for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
              {
                if(minSMR > iteratorDistrict.getResult().smr)
                {
                  minSMR = iteratorDistrict.getResult().smr;
                }
                if(maxSMR < iteratorDistrict.getResult().smr)
                {
                  maxSMR = iteratorDistrict.getResult().smr;
                }
              }
              int[] settlementDistributions = new int[DISTRIBUTIONS_LENGTH];
              int[] populationDistributions = new int[DISTRIBUTIONS_LENGTH];
              com.lowagie.text.pdf.PdfPTable countyTable = new com.lowagie.text.pdf.PdfPTable(3);
              countyTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
              countyTable.setSpacingBefore(10);
              countyTable.setWidthPercentage(75);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.No", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              countyTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.County", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              countyTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Settlements", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              countyTable.addCell(cell);
              countyTable.setHeaderRows(1);
              com.lowagie.text.pdf.PdfPTable settlementTable = new com.lowagie.text.pdf.PdfPTable(8);
              com.lowagie.text.pdf.PdfPTable lowSettlementTable = new com.lowagie.text.pdf.PdfPTable(8);
              com.lowagie.text.pdf.PdfPTable highSettlementTable = new com.lowagie.text.pdf.PdfPTable(8);
              settlementTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
              lowSettlementTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
              highSettlementTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
              settlementTable.setSpacingBefore(10);
              lowSettlementTable.setSpacingBefore(10);
              highSettlementTable.setSpacingBefore(10);
              settlementTable.setWidths(new int[]{30, 10, 10, 7, 15, 10, 10, 15});
              lowSettlementTable.setWidths(new int[]{30, 10, 10, 7, 15, 10, 10, 15});
              highSettlementTable.setWidths(new int[]{30, 10, 10, 7, 15, 10, 10, 15});
              settlementTable.setWidthPercentage(100);
              lowSettlementTable.setWidthPercentage(100);
              highSettlementTable.setWidthPercentage(100);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Name", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Population", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.District", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR.Significance", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.SMR.Smoothed", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Trend", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Trend.Significance", iteratorLocale), textFont));
              cell.setBorderWidth(2);
              settlementTable.addCell(cell);
              lowSettlementTable.addCell(cell);
              highSettlementTable.addCell(cell);
              settlementTable.setHeaderRows(1);
              lowSettlementTable.setHeaderRows(1);
              highSettlementTable.setHeaderRows(1);
              elv.util.parameters.Settlement county = null;
              int countyCount = 0;
              int totalSettlementCount = 0;
              int countySettlementCount = 0;
              int lowSettlementCount = 0;
              int highSettlementCount = 0;
              int lowSettlementPopulation = 0;
              int highSettlementPopulation = 0;
              boolean countableCounty = false;
              boolean addedCounty = false;
              boolean lowAddedCounty = false;
              boolean highAddedCounty = false;
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
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(countyCount, iteratorLocale), textFont));
                    countyTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(county.getName(), textFont));
                    countyTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(countySettlementCount, iteratorLocale), textFont));
                    countyTable.addCell(cell);
                    countySettlementCount = 0;
                    countableCounty = false;
                    addedCounty = false;
                    lowAddedCounty = false;
                    highAddedCounty = false;
                  }
                  county = iteratorSettlement;
                }
                for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
                {
                  if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() && iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()) &&
                     iteratorSettlement.getYearResults().size() > 0)
                  {
                    if(iteratorSettlement.getParagraph().contains(county.getParagraph()) && !addedCounty)
                    {
                      countableCounty = true;
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(county.getName(), boldTextFont));
                      cell.setColspan(8);
                      settlementTable.addCell(cell);
                      addedCounty = true;
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
                    quotient = minSMR + (maxSMR - minSMR) / DISTRIBUTIONS_LENGTH;
                    int distribution;
                    for(distribution = 0; distribution < DISTRIBUTIONS_LENGTH - 1; distribution++)
                    {
                      if(iteratorDistrict.getResult().smr <= quotient)
                      {
                        break;
                      }
                      else
                      {
                        quotient += (maxSMR - minSMR) / DISTRIBUTIONS_LENGTH;
                      }
                    }
                    settlementDistributions[distribution] ++;
                    populationDistributions[distribution] += settlementPopulation;
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorSettlement.getName(), textFont));
                    settlementTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(settlementPopulation, iteratorLocale), textFont));
                    settlementTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getCode(), iteratorLocale), textFont));
                    settlementTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smr, iteratorLocale), textFont));
                    settlementTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smrSignificance, iteratorLocale), textFont));
                    settlementTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smoothSmr, iteratorLocale), textFont));
                    settlementTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().trend, iteratorLocale), textFont));
                    settlementTable.addCell(cell);
                    cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorDistrict.getResult().trendSignificance, textFont));
                    settlementTable.addCell(cell);
                    if(distribution == 0)
                    {
                      if(iteratorSettlement.getParagraph().contains(county.getParagraph()) && !lowAddedCounty)
                      {
                        cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(county.getName(), boldTextFont));
                        cell.setColspan(8);
                        lowSettlementTable.addCell(cell);
                        lowAddedCounty = true;
                      }
                      lowSettlementCount++;
                      lowSettlementPopulation += settlementPopulation;
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorSettlement.getName(), textFont));
                      lowSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(settlementPopulation, iteratorLocale), textFont));
                      lowSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getCode(), iteratorLocale), textFont));
                      lowSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smr, iteratorLocale), textFont));
                      lowSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smrSignificance, iteratorLocale), textFont));
                      lowSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smoothSmr, iteratorLocale), textFont));
                      lowSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().trend, iteratorLocale), textFont));
                      lowSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorDistrict.getResult().trendSignificance, textFont));
                      lowSettlementTable.addCell(cell);
                    }
                    else if(distribution == DISTRIBUTIONS_LENGTH - 1)
                    {
                      if(iteratorSettlement.getParagraph().contains(county.getParagraph()) && !highAddedCounty)
                      {
                        cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(county.getName(), boldTextFont));
                        cell.setColspan(8);
                        highSettlementTable.addCell(cell);
                        highAddedCounty = true;
                      }
                      highSettlementCount++;
                      highSettlementPopulation += settlementPopulation;
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorSettlement.getName(), textFont));
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorSettlement.getName(), textFont));
                      highSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(settlementPopulation, iteratorLocale), textFont));
                      highSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getCode(), iteratorLocale), textFont));
                      highSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smr, iteratorLocale), textFont));
                      highSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smrSignificance, iteratorLocale), textFont));
                      highSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().smoothSmr, iteratorLocale), textFont));
                      highSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(iteratorDistrict.getResult().trend, iteratorLocale), textFont));
                      highSettlementTable.addCell(cell);
                      cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(iteratorDistrict.getResult().trendSignificance, textFont));
                      highSettlementTable.addCell(cell);
                    }
                    break;
                  }
                }
              }
              if(county != null && countableCounty)
              {
                countyCount++;
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(countyCount, iteratorLocale), textFont));
                countyTable.addCell(cell);
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(county.getName(), textFont));
                countyTable.addCell(cell);
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(countySettlementCount, iteratorLocale), textFont));
                countyTable.addCell(cell);
              }
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.translate("Documenting.Header.Total", iteratorLocale), textFont));
              countyTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(countyCount, iteratorLocale), textFont));
              countyTable.addCell(cell);
              cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(elv.util.Util.format(totalSettlementCount, iteratorLocale), textFont));
              countyTable.addCell(cell);

              // Chapter 4-5, Section, Subsection 3.
              subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section3", iteratorLocale), subSectionFont), 3);
              double minDistribution = java.lang.Double.MAX_VALUE;
              double maxDistribution = 0;
              for(java.lang.Integer iteratorDistribution : settlementDistributions)
              {
                if(minDistribution > iteratorDistribution)
                {
                  minDistribution = iteratorDistribution;
                }
                if(maxDistribution < iteratorDistribution)
                {
                  maxDistribution = iteratorDistribution;
                }
              }
              width = 470;
              height = 320;
              leftMargin = 20;
              bottomMargin = 30;
              topMargin = 50;
              rightMargin = 10;
              java.awt.Color barColor = new java.awt.Color(213, 174, 68);
              template = pdfWriter.getDirectContent().createTemplate(width, height);
              template.moveTo(leftMargin, bottomMargin);
              template.lineTo(width - rightMargin, bottomMargin);
              template.setColorStroke(baseColor);
              template.stroke();
              xOffset = (width - leftMargin - rightMargin) / DISTRIBUTIONS_LENGTH;
              yOffset = (height - bottomMargin - topMargin) / DISTRIBUTIONS_LENGTH;
              yAxisLength = height - bottomMargin - topMargin;
              float[] distributionHeights = new float[DISTRIBUTIONS_LENGTH];
              for(int distributionCount = 0; distributionCount < DISTRIBUTIONS_LENGTH; distributionCount++)
              {
                // Bars.
                distributionHeights[distributionCount] = (float)(settlementDistributions[distributionCount] - minDistribution)  / (float)(maxDistribution - minDistribution) * yAxisLength;
                template.rectangle(leftMargin + distributionCount * xOffset, bottomMargin, xOffset, distributionHeights[distributionCount]);
                template.setColorFill(barColor);
                template.fill();
                template.rectangle(leftMargin + distributionCount * xOffset, bottomMargin, xOffset, distributionHeights[distributionCount]);
                template.setColorStroke(new java.awt.Color(0, 0, 0));
                template.stroke();
              }
              template.setColorStroke(baseColor);
              template.setColorFill(baseColor);
              template.beginText();
              fontSize = textFont.getCalculatedSize();
              template.setFontAndSize(baseFont, fontSize);
              template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.translate("Documenting.Header.Settlements", iteratorLocale), fontSize, bottomMargin + (height - bottomMargin - topMargin) / 2, 90);
              template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.translate("Documenting.Header.Distribution", iteratorLocale), leftMargin + (width - leftMargin - rightMargin) / 2, fontSize, 0);
              textLineGap = 2;
              for(int distributionCount = 0; distributionCount < DISTRIBUTIONS_LENGTH; distributionCount++)
              {
                template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_RIGHT, elv.util.Util.format(distributionCount + 1, iteratorLocale), leftMargin + (distributionCount + 1) * xOffset + fontSize / 2, bottomMargin - textLineGap, 90);
                if(settlementDistributions[distributionCount] != 0)
                {
                  template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.format(settlementDistributions[distributionCount], iteratorLocale), leftMargin + distributionCount * xOffset + xOffset / 2, bottomMargin + distributionHeights[distributionCount] + textLineGap, 0);
                }
              }
              template.endText();
              image = com.lowagie.text.Image.getInstance(template);
              com.lowagie.text.pdf.PdfPTable barTable = new com.lowagie.text.pdf.PdfPTable(1);
              barTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
              barTable.setSpacingBefore(10);
              barTable.setWidthPercentage(100);
              cell = new com.lowagie.text.pdf.PdfPCell(image);
              cell.setBorderWidth(0);
              barTable.addCell(cell);
              subSection.add(barTable);
              // Chapter 4-5, Section, Subsection 4.
              subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section4", iteratorLocale), subSectionFont), 3);
              minDistribution = java.lang.Double.MAX_VALUE;
              maxDistribution = 0;
              for(java.lang.Integer iteratorDistribution : populationDistributions)
              {
                if(minDistribution > iteratorDistribution)
                {
                  minDistribution = iteratorDistribution;
                }
                if(maxDistribution < iteratorDistribution)
                {
                  maxDistribution = iteratorDistribution;
                }
              }
              width = 470;
              height = 320;
              leftMargin = 20;
              bottomMargin = 30;
              topMargin = 50;
              rightMargin = 10;
              barColor = new java.awt.Color(227, 163, 44);
              template = pdfWriter.getDirectContent().createTemplate(width, height);
              template.moveTo(leftMargin, bottomMargin);
              template.lineTo(width - rightMargin, bottomMargin);
              template.setColorStroke(baseColor);
              template.stroke();
              xOffset = (width - leftMargin - rightMargin) / DISTRIBUTIONS_LENGTH;
              yOffset = (height - bottomMargin - topMargin) / DISTRIBUTIONS_LENGTH;
              yAxisLength = height - bottomMargin - topMargin;
              for(int distributionCount = 0; distributionCount < DISTRIBUTIONS_LENGTH; distributionCount++)
              {
                // Bars.
                distributionHeights[distributionCount] = (float)(populationDistributions[distributionCount] - minDistribution)  / (float)(maxDistribution - minDistribution) * yAxisLength;
                template.rectangle(leftMargin + distributionCount * xOffset, bottomMargin, xOffset, distributionHeights[distributionCount]);
                template.setColorFill(barColor);
                template.fill();
                template.rectangle(leftMargin + distributionCount * xOffset, bottomMargin, xOffset, distributionHeights[distributionCount]);
                template.setColorStroke(new java.awt.Color(0, 0, 0));
                template.stroke();
              }
              template.setColorStroke(baseColor);
              template.setColorFill(baseColor);
              template.beginText();
              fontSize = textFont.getCalculatedSize();
              template.setFontAndSize(baseFont, fontSize);
              template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.translate("Documenting.Header.Population", iteratorLocale), fontSize, bottomMargin + (height - bottomMargin - topMargin) / 2, 90);
              template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.translate("Documenting.Header.Distribution", iteratorLocale), leftMargin + (width - leftMargin - rightMargin) / 2, fontSize, 0);
              textLineGap = 2;
              for(int distributionCount = 0; distributionCount < DISTRIBUTIONS_LENGTH; distributionCount++)
              {
                template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_RIGHT, elv.util.Util.format(distributionCount + 1, iteratorLocale), leftMargin + (distributionCount + 1) * xOffset + fontSize / 2, bottomMargin - textLineGap, 90);
                if(populationDistributions[distributionCount] != 0)
                {
                  template.showTextAligned(com.lowagie.text.pdf.PdfContentByte.ALIGN_CENTER, elv.util.Util.format(populationDistributions[distributionCount], iteratorLocale), leftMargin + distributionCount * xOffset + xOffset / 2, bottomMargin + distributionHeights[distributionCount] + textLineGap, 0);
                }
              }
              template.endText();
              image = com.lowagie.text.Image.getInstance(template);
              barTable = new com.lowagie.text.pdf.PdfPTable(1);
              barTable.setHorizontalAlignment(com.lowagie.text.pdf.PdfPTable.ALIGN_LEFT);
              barTable.setSpacingBefore(10);
              barTable.setWidthPercentage(100);
              cell = new com.lowagie.text.pdf.PdfPCell(image);
              cell.setBorderWidth(0);
              barTable.addCell(cell);
              subSection.add(barTable);
              // Chapter 4-5, Section, Subsection 5.
              subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section5", iteratorLocale), subSectionFont), 3);
              subSection.add(countyTable);
              // Chapter 4-5, Section, Subsection 6.
              subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section6", iteratorLocale), subSectionFont), 3);
              subSection.add(settlementTable);
              // Chapter 4-5, Section, Subsection 7.
              subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section7", iteratorLocale), subSectionFont), 3);
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section7.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(minSMR, iteratorLocale)).replaceFirst("%1", elv.util.Util.format(minSMR + (maxSMR - minSMR) / 10, iteratorLocale));
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section7.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(lowSettlementCount, iteratorLocale)).replaceFirst("%1", elv.util.Util.format((double)lowSettlementPopulation * 100 / iteratorSpot.getResult().population, iteratorLocale));
              subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
              subSection.add(lowSettlementTable);
              // Chapter 4-5, Section, Subsection 8.
              subSection = section.addSection(new com.lowagie.text.Paragraph(elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section8", iteratorLocale), subSectionFont), 3);
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section8.Paragraph1", iteratorLocale).replaceFirst("%0", elv.util.Util.format(maxSMR, iteratorLocale)).replaceFirst("%1", elv.util.Util.format(maxSMR - (maxSMR - minSMR) / 10, iteratorLocale));
              paragraphLine = elv.util.Util.translate("Documenting.Region.Chapter4-5.Section.Section7.Paragraph2", iteratorLocale).replaceFirst("%0", elv.util.Util.format(highSettlementCount, iteratorLocale)).replaceFirst("%1", elv.util.Util.format((double)highSettlementPopulation * 100 / iteratorSpot.getResult().population, iteratorLocale));
              subSection.add(new com.lowagie.text.Paragraph(paragraphLine, textFont));
              subSection.add(highSettlementTable);
            }
            execution.getProgresses().peek().setValue(spotCount + 1);
          }
          execution.getProgresses().pop();
          document.add(chapter);
        }
        execution.getProgresses().peek().setValue(iteratorFavorable + 1);
      }
      execution.getProgresses().pop();
      document.close();
      execution.getProgresses().peek().setValue(localeCount + 1);
    }
    execution.getProgresses().pop();
    setDone(execution.getTask(), true);
    return true;
  }
  
}
