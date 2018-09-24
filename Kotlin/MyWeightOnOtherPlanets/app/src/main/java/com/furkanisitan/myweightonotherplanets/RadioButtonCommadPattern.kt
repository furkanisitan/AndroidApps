package com.furkanisitan.myweightonotherplanets

private const val KILOGRAM_TO_POUND = 2.2046
private const val POUND_TO_KILOGRAM = .4536
private const val MERCURY = .38
private const val VENUS = .91
private const val MARS = .38
private const val JUPITER = 2.34
private const val SATURN = 1.06
private const val URANUS = .92
private const val NEPTUNE = 1.19
private const val PLUTO = .06

// Invoker
class RadioButtonInvoker constructor(private val weight: Double) {

    private val commands = mapOf(
            R.id.cbMercury to MercuryCommand(),
            R.id.cbVenus to VenusCommand(),
            R.id.cbMars to MarsCommand(),
            R.id.cbJupiter to JupiterCommand(),
            R.id.cbSaturn to SaturnCommand(),
            R.id.cbUranus to UranusCommand(),
            R.id.cbNeptune to NeptuneCommand(),
            R.id.cbPluto to PlutoCommand()
    )

    fun invoke(id: Int): Double = commands[id]!!.execute(weight)
}

// Receiver
private class RadioButtonReceiver {

    fun mercury(kilogram: Double) = multiple(kilogram, MERCURY)

    fun venus(kilogram: Double) = multiple(kilogram, VENUS)

    fun mars(kilogram: Double) = multiple(kilogram, MARS)

    fun jupiter(kilogram: Double) = multiple(kilogram, JUPITER)

    fun saturn(kilogram: Double) = multiple(kilogram, SATURN)

    fun uranus(kilogram: Double) = multiple(kilogram, URANUS)

    fun neptune(kilogram: Double) = multiple(kilogram, NEPTUNE)

    fun pluto(kilogram: Double) = multiple(kilogram, PLUTO)

    private fun multiple(kilogram: Double, factor: Double) =
            poundToKilogram(kilogramToPound(kilogram)) * factor

    private fun kilogramToPound(kilogram: Double): Double = KILOGRAM_TO_POUND * kilogram

    private fun poundToKilogram(pound: Double): Double = POUND_TO_KILOGRAM * pound
}

// abstract command
private abstract class RadioButtonCommand {

    protected val radioButtonReceiver = RadioButtonReceiver()

    abstract fun execute(kilogram: Double): Double
}

// Commands
private class MarsCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.mars(kilogram)
}

private class MercuryCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.mercury(kilogram)
}

private class VenusCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.venus(kilogram)
}

private class JupiterCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.jupiter(kilogram)
}

private class SaturnCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.saturn(kilogram)
}

private class UranusCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.uranus(kilogram)
}

private class NeptuneCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.neptune(kilogram)
}

private class PlutoCommand : RadioButtonCommand() {

    override fun execute(kilogram: Double): Double = radioButtonReceiver.pluto(kilogram)
}
