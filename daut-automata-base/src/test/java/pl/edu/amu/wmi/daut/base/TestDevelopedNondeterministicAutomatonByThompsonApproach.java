package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;

/**
 * Test klasy DevelopedNondeterministicAutomatonByThompsonApproach.
 */
public class TestDevelopedNondeterministicAutomatonByThompsonApproach extends TestCase {

    /**
     * Test pierwszy.
     */
    public final void testSimpleAutomaton() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.addLoop(q0, new CharTransitionLabel('b'));
        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addLoop(q1, new CharTransitionLabel('b'));
        spec.addTransition(q1, q2, new CharTransitionLabel('a'));
        spec.addLoop(q2, new CharTransitionLabel('a'));
        spec.addLoop(q2, new CharTransitionLabel('b'));

        spec.markAsInitial(q0);
        spec.markAsFinal(q1);

        final DevelopedNondeterministicAutomatonByThompsonApproach automaton
            = new DevelopedNondeterministicAutomatonByThompsonApproach(
                spec);

        assertTrue(automaton.accepts("a"));
        assertTrue(automaton.accepts("ba"));
        assertTrue(automaton.accepts("ab"));
        assertTrue(automaton.accepts("bab"));
        assertTrue(automaton.accepts("bba"));
        assertTrue(automaton.accepts("abb"));
        assertFalse(automaton.accepts("b"));
        assertFalse(automaton.accepts("aa"));
        assertFalse(automaton.accepts("bb"));
        assertFalse(automaton.accepts("aba"));
        assertFalse(automaton.accepts("baab"));
        assertFalse(automaton.accepts("bbb"));
    }

    /**
     * Test drugi (Epsislon-przejscia).
     */
    public final void testOnlyEpsilonTransitionLabel() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();

        spec.addTransition(q0, q1, new EpsilonTransitionLabel());
        spec.addTransition(q1, q2, new EpsilonTransitionLabel());

        spec.markAsInitial(q0);
        spec.markAsFinal(q2);

        final DevelopedNondeterministicAutomatonByThompsonApproach automaton
            = new DevelopedNondeterministicAutomatonByThompsonApproach(
                spec);

        assertTrue(automaton.accepts(""));
        assertFalse(automaton.accepts("ala"));
    }

    /**
     * Test trzeci (Automat pusty).
     */
    public final void testEmptyAutomaton() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        final DevelopedNondeterministicAutomatonByThompsonApproach automaton 
            = new DevelopedNondeterministicAutomatonByThompsonApproach(spec);

        assertFalse(automaton.accepts("aaa"));
        assertFalse(automaton.accepts("bbb"));
        assertFalse(automaton.accepts("abc"));
    }

    /**
     * Test czwarty (Pierwsze trzy epsilon przejscia).
     */
    public final void testOnlyEpsilonTransitionLabelFromInitialState(){
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();
        State q3 = spec.addState();
        State q4 = spec.addState();
        State q5 = spec.addState();
        State q6 = spec.addState();

        spec.addTransition(q0, q1, new EpsilonTransitionLabel());
        spec.addTransition(q0, q2, new EpsilonTransitionLabel());
        spec.addTransition(q0, q3, new EpsilonTransitionLabel());
        spec.addTransition(q1, q4, new CharTransitionLabel('a'));
        spec.addTransition(q2, q4, new CharTransitionLabel('b'));
        spec.addTransition(q3, q4, new CharTransitionLabel('c'));
        spec.addTransition(q3, q5, new CharTransitionLabel('a'));
        spec.addTransition(q3, q5, new CharTransitionLabel('b'));
        spec.addTransition(q4, q6, new CharTransitionLabel('a'));
        spec.addTransition(q4, q5, new CharTransitionLabel('a'));
        spec.addTransition(q5, q6, new CharTransitionLabel('c'));
        spec.addTransition(q5, q4, new CharTransitionLabel('b'));
        spec.addLoop(q3, new CharTransitionLabel('c'));
        spec.addLoop(q5, new CharTransitionLabel('c'));
        spec.addLoop(q6, new CharTransitionLabel('a'));
        spec.addLoop(q6, new CharTransitionLabel('b'));
        spec.addLoop(q6, new CharTransitionLabel('c'));

        spec.markAsInitial(q0);
        spec.markAsFinal(q6);

        final DevelopedNondeterministicAutomatonByThompsonApproach automaton
            = new DevelopedNondeterministicAutomatonByThompsonApproach(
                spec);

        assertTrue(automaton.accepts("aa"));
        assertTrue(automaton.accepts("ba"));
        assertTrue(automaton.accepts("cca"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("abc"));
        assertFalse(automaton.accepts("amu"));
    }

    /**
     * Test piaty (Jeden stan, brak przejsc).
     */
    public final void testOneState() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();

        spec.markAsInitial(q0);
        spec.markAsFinal(q0);

        final DevelopedNondeterministicAutomatonByThompsonApproach automaton
            = new DevelopedNondeterministicAutomatonByThompsonApproach(
                spec);

        assertTrue(automaton.accepts(""));
        assertFalse(automaton.accepts("a"));
        assertFalse(automaton.accepts("b"));
        assertFalse(automaton.accepts("ccc"));
    }

    /**
     * Test szosty (same epsilon przejscia na koncu).
     */
    public final void testOnlyEpsilonTransitionLabelToFinalState() {
        final AutomatonSpecification spec = new NaiveAutomatonSpecification();

        State q0 = spec.addState();
        State q1 = spec.addState();
        State q2 = spec.addState();
        State q3 = spec.addState();

        spec.addTransition(q0, q1, new CharTransitionLabel('a'));
        spec.addTransition(q0, q2, new CharTransitionLabel('b'));
        spec.addTransition(q1, q3, new EpsilonTransitionLabel());
        spec.addTransition(q2, q3, new EpsilonTransitionLabel());

        spec.markAsInitial(q0);
        spec.markAsFinal(q3);

        final DevelopedNondeterministicAutomatonByThompsonApproach automaton
            = new DevelopedNondeterministicAutomatonByThompsonApproach(
                spec);

        assertTrue(automaton.accepts("a"));
        assertTrue(automaton.accepts("b"));
        assertFalse(automaton.accepts(""));
        assertFalse(automaton.accepts("aa"));
    }
}
