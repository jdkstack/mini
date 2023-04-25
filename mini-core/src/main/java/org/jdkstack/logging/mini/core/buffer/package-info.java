/**
 * 将byte[]或者char[]输出到文件中.
 *
 * <p>此工具为日志框架量身定做。
 *
 * <pre>
 *   1.MmapByteArrayWriter使用mmap的方式写文件,仅支持按照文件大小切割文件.
 *   2.ByteArrayWriter将字节数组写入文件,仅支持按照文件大小和文件行数切割文件.
 *   3.CharArrayWriter将字符数组写入文件,仅支持按照文件大小和文件行数切割文件.
 *   (可能会支持按照日期时间切割文件.)
 * </pre>
 *
 * @author admin
 */
package org.jdkstack.logging.mini.core.buffer;
