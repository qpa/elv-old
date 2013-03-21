package org.oki.elv.common.face.step.result;

/**
 * Result.
 * @author Elv
 * @param <K> the type of key.
 * @param <V> the type of value.
 */
public class Result<K, V> {
  /** Separator betveen key and value. */
  public static final String SEPARATOR = ";";
  
  /** The key object. */
  private K key;
  /** The value object. */
  private V value;
  /** Is header. */
  private boolean isHeader;
  
  /**
   * Contructor.
   * @param isHeader
   */
  public Result(boolean isHeader) {
    this.isHeader = isHeader;
  }

  /**
   *  Getter of the key.
   *  @return the key.
   */
  public K getKey() {
    return key;
  }
  
  /**
   *  Setter of the key.
   *  @param key the key to set.
   */
  public void setKey(K key) {
    this.key = key;
  }
  
  /**
   *  Getter of the value.
   *  @return the value.
   */
  public V getValue() {
    return value;
  }
  
  /**
   *  Setter of the value.
   *  @param value the value.
   */
  public void setValue(V value) {
    this.value = value;
  }
  
  /**
   * Getter of the isHeader.
   * @return the isHeader.
   */
  public final boolean isHeader() {
    return isHeader;
  }

  @Override
  public String toString() {
    return key + SEPARATOR + value;
  }
}
