package br.pucpr.table;

import java.util.ArrayList;
import java.util.List;

public class Table {
  private static final String RIGHT_MARGIN = " ".repeat(20);

  private final TableData data;

  public Table(TableData data) {
    this.data = data;
  }

  public void print(boolean alignRight, Theme theme) {
    if (data.getRowCount() == 0) {
      System.out.println("ERRO: Lista vazia ou nula.");
      return;
    }
    final var margin = alignRight ? RIGHT_MARGIN : "";
    for (var line : render(theme)) {
      System.out.println(margin + line);
    }
  }

  private List<String> render(Theme theme) {
    final var border = theme.getBorderChar().repeat(borderWidth());
    final var lines = new ArrayList<String>();
    lines.add(border);
    lines.add(formatHeader());
    lines.add(border);
    for (var row = 0; row < data.getRowCount(); row++) {
      lines.add(formatRow(row));
    }
    lines.add(border);
    return lines;
  }

  private String formatHeader() {
    final var sb = new StringBuilder("|");
    for (var column = 0; column < data.getColumnCount(); column++) {
      sb.append(' ').append(data.getHeader(column)).append(" |");
    }
    return sb.toString();
  }

  private String formatRow(int row) {
    final var sb = new StringBuilder("|");
    for (var column = 0; column < data.getColumnCount(); column++) {
      final var alignment = data.isRightAligned(column) ? "" : "-";
      final var format = "%" + alignment + width(column) + "s";
      sb.append(' ').append(String.format(format, data.getValue(row, column))).append(" |");
    }
    return sb.toString();
  }

  private int width(int column) {
    return data.getHeader(column).length();
  }

  private int borderWidth() {
    var total = 1;
    for (var column = 0; column < data.getColumnCount(); column++) {
      total += width(column) + 3;
    }
    return total;
  }
}
