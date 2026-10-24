package br.pucpr.table;

/**
 * Representa os dados de uma tabela. A largura de cada coluna é dada pela largura do seu
 * cabeçalho.
 */
public interface TableData {
  int getColumnCount();

  String getHeader(int column);

  int getRowCount();

  String getValue(int row, int column);

  default boolean isRightAligned(int column) {
    return false;
  }
}
