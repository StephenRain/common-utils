package org.code.common.util;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yaotianchi
 * @date 2019/10/9
 */
@Data
@Accessors(chain = true)
public class KVPair<K,V> {

    private K k;

    private V v;

    public  KVPair of(K k,V v){
        return this.setK(k).setV(v);
    }

}
