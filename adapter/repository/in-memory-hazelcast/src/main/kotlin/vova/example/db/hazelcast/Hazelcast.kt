package vova.example.db.hazelcast

import com.hazelcast.core.HazelcastInstance

internal object Hazelcast {

    private val LOCK = Any()
    private var HAZELCAST: HazelcastInstance? = null

    val instance: HazelcastInstance
        get() {
            if (HAZELCAST == null) {
                synchronized(LOCK) {
                    if (HAZELCAST == null) {
                        HAZELCAST = com.hazelcast.core.Hazelcast.newHazelcastInstance()
                    }
                }
            }
            return HAZELCAST!!
        }
}
