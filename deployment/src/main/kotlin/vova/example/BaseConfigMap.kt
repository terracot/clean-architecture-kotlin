package vova.example

import com.fkorotkov.kubernetes.metadata
import io.fabric8.kubernetes.api.model.ConfigMap
import java.io.File

class BaseConfigMap : ConfigMap {
    constructor(cmName: String) {
        metadata {
            name = "$cmName-service-configmap"
            labels = mapOf(
                "app" to cmName,
                "tier" to "backend"
            )
        }
        data = mapOf(
            "application.yml" to File("path/to/yaml").readText(),
            "sentry.conf" to File("sentry.conf").readText()
        )
    }

}

