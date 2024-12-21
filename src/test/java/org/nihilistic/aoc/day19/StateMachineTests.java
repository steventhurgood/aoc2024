package org.nihilistic.aoc.day19;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateMachineTests {
    private static final Logger logger = LoggerFactory.getLogger(StateMachineTests.class);

    @Test
    public void testStateMachine_whenAddState_succeeds() {
        var input = "r, wr, b, g, bwu, rb, gb, br";
        var stateMachine = StateMachine.fromString(input);

        assertThat(stateMachine.states()).isNotEmpty();
    }

    @Test
    public void testStateMachine_whenMatches_isTrue() {
        var input = "r, wr, b, g, bwu, rb, gb, br";
        var stateMachine = StateMachine.fromString(input);

        String tests = """
                brwrr
                bggr
                gbbr
                rrbgbr
                ubwu
                bwurrg
                brgr
                bbrgwb
                                                """;
        var actual = tests.lines().map(stateMachine::matches).toList();
        assertThat(actual).containsExactly(2L, 1L, 4L, 6L, 1L, 2L, 0L, 0L).inOrder();
    }

    @Test
    public void testStateMachine_whenBigMatches_isTrue() {
        var input = "ububwuu, ub, gbwbgr, rrur, ggbuwg, uuubrwb, bww, brug, bgb, ugrbb, ggwuu, uur, wbwr, gug, gbgub, rru, urwbw, bgwgu, gguguu, wbgguuw, buubuuu, bgugrbr, wrwbr, ug, rrww, ugug, urr, ugbgbw, rugg, wrguuw, rwwbwwww, wgwbbr, bbur, wwgwr, rrug, rugugr, bbrw, wbgruw, urwbbw, bgrwgb, rgwgrr, wuruur, bruub, wg, wwguwu, bggbubbg, bu, urb, rugwuuu, gwgww, wubbg, gbb, urw, bgwrb, rrwwu, gurbrg, ggubgu, rub, rgub, buwug, rbguwwb, bgrur, bguuguu, uggbu, rug, rgrw, wr, wbbrw, ugwrwb, bugbubg, uwrb, gwwwuu, rgw, uw, grbrr, ugbwbgg, ubrr, wug, rrbgw, buru, bwb, rgwrg, gwrwubb, buruurw, bwgwg, gwbruuw, bwbr, gw, br, wgugg, gurwb, www, rgggg, rgu, grwgrwr, uru, uwu, wwuugbr, gbr, gbbwgw, wwbbrb, uubg, brgwbrr, gww, ubbg, g, bwwg, rrbb, bug, uburg, guu, rubruw, rrbr, rgwrrug, urbwrub, rubggrgb, uggw, wgu, ugw, bubbw, uwbu, wrgg, ubgub, wbu, bgrrb, gwbb, rgbu, grgu, rgb, wbw, bbbb, gbwbwbw, bur, wu, gub, wub, brg, gbrw, gwu, ru, uugw, ggwbrb, ruu, rgru, rb, ubuu, gurgbu, urww, gru, uwbb, wburw, ugbbrwgr, gg, wur, bwgbb, bgggr, wrur, buwub, uwwguuu, uww, bugb, bbg, bugbu, rubg, wuu, grgbug, wrbg, ggw, uugu, rburug, bgru, bbwuwg, gbubg, urwwgw, buu, wwb, rguu, brb, wwrwg, grb, gwbrgw, ugu, wbruu, ubb, rubgw, grrbr, r, ubbwub, urrg, rgbgu, gbwrggwb, wrg, rrr, rw, guw, wrbruurg, rgr, grrub, rrgguug, rrw, uwuu, grwu, uug, rr, ubu, gggrr, guburg, guwrb, wgwrugb, rbbgu, ugbru, buwwbur, rwwr, wwgr, bwg, rgwgg, gbgrur, wguwg, ggbrb, wgguwgg, bwr, grgur, gbwuggr, gu, urrgrw, uburu, wrbrwbwu, gruwgr, wgbgubrw, gbbg, ubw, grwwrbu, wbgw, rrbgr, rwr, ggu, bgbbwu, ww, ur, ubuuwr, wwugb, urrb, grr, grrw, rwg, wwgurwr, grbr, uub, rguurrg, uurg, urrggbu, rbrw, uwg, rgrrubgb, grbur, grgb, rurg, ugrbr, rww, bwrugu, bgwg, uguw, uwwu, ggrgbg, grg, wwu, rbgr, rggrbwuw, bgr, bwrw, wbuu, wrwbwu, wgr, ugwwwg, gggu, rwwrbrb, rrg, wrrr, rurb, wubruruw, brr, ugubw, gbuuwr, uu, gwb, bwru, wbwggug, bgu, gggrbrrg, wubu, grw, gbu, ruuu, bbwb, bw, wuw, ugrw, brurb, wuwrbr, wrb, brw, rbg, bgurb, uuw, uwur, rwwruwgu, gbuuug, urwrwugr, bgrr, gwr, wgwr, ggugu, wburwrb, rggur, ggg, rbb, gguu, wb, rwwgu, rbw, grrbuuu, rrgg, wrw, wwbg, wwg, wrww, gbug, bwubwuu, wru, ubrguu, wggg, rg, wwwb, ubr, bbw, rgwwbwwr, urbwub, rwb, urub, gguug, rburru, u, rgwr, ruwurru, rggwg, wbg, ugwbbwb, ruub, wbuw, ggr, gwwgb, bbbrru, rbub, rur, rwubbrru, bruuwg, rrrw, gwggww, rwu, rggw, gubuuw, gugbbggw, ubg, wgw, bwug, wwuruu, wbb, ubguub, rgg, brrwr, wwrgb, brrbg, uwb, w, gubuu, uggbbr, uwr, ugwg, ggwbww, bwu, ubgbu, rbbbu, brgr, bwub, uuwgu, gb, bbu, urg, rurwrbrr, rrb, ugbbru, grwrw, wwr, wbbbg, ggurbgu, rrbgrbur, rbu, gbg, rwwwr, ugr, ugrrb, uwwbugu, gbuuwg, rguubu, urruu, brgub, gwg, uuu, bgw, rgrgb, rrurb, uggr, rgbbrg, burr, wbru, wuug, rwgubuww, ugb, gggr, ugg, ruuug, brrg, wuur, wuwb, grgrrb, wgb, bb, bbr, bbrgbw, grgbr, urbb, gbw, bub, ruw, ggb, bru, wbr, wgg, grrwug, uwwgr, brru, gggbgrw, rrurbgu, wruw, wrr, ubbgb, bgguwb";
        var stateMachine = StateMachine.fromString(input);
        logger.info("\n" + stateMachine.toString());

        String tests = """
                gwbrbguruugbwubwuurubwrwguwugwwrgwwbburbgubrbgwbbgwwuwu
                wbrbrgrwgwgwgbgrwgrrwwuugbbugrgbgwrugrbwgrbrwbgbuwwwrbubwu
                bggrrubugrrwubwrrbrrugrguubwwuurubuwgrurbbuubwuwrurgrgbu
                bggwurgggburgwrrwurbwrbbggguwwugggubwwwgbrwuuwuubggbg
                                """;
        var actual = tests.lines().map(stateMachine::matches).toList();
        assertThat(actual).containsExactly(true, true, false, false).inOrder();
    }
}
