package org.microg.gms.location.manager

enum class Action { ALLOW, SPOOF, BLOCK, DEGRADE, DELAY }

data class Decision(
    val pkg: String,
    val action: Action,
    val lat: Double? = null,
    val lon: Double? = null
)

interface PolicyEngine {
    fun decide(pkg: String): Decision
}

class InMemoryPolicy : PolicyEngine {
    private val rules = mutableMapOf<String, Decision>()
    fun put(rule: Decision) { rules[rule.pkg] = rule }
    override fun decide(pkg: String) = rules[pkg] ?: Decision(pkg, Action.ALLOW)
}

object PolicyEngineHolder {
    val instance = InMemoryPolicy()
}
