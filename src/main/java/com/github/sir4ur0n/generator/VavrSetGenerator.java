package com.github.sir4ur0n.generator;

import static com.pholser.junit.quickcheck.internal.Ranges.Type.INTEGRAL;
import static com.pholser.junit.quickcheck.internal.Ranges.checkRange;

import com.google.auto.service.AutoService;
import com.pholser.junit.quickcheck.generator.ComponentizedGenerator;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

@AutoService(Generator.class)
public class VavrSetGenerator extends ComponentizedGenerator<Set> {

  private Size sizeRange;

  public VavrSetGenerator() {
    super(Set.class);
  }

  /**
   * <p>Tells this generator to add elements to the generated collection
   * a number of times within a specified maximum, inclusive,
   * chosen with uniform distribution.</p>
   *
   * <p>Note that some kinds of collections have a finite number of distinct elements, so the
   * number of elements added may not be equal to the collection's size
   *
   * @param size annotation that gives the size constraints
   */
  @SuppressWarnings("unused")
  public void configure(Size size) {
    this.sizeRange = size;
    checkRange(INTEGRAL, size.min(), size.max());
  }

  @Override
  public Set<?> generate(SourceOfRandomness random, GenerationStatus status) {
    return HashSet.fill(size(random), () -> componentGenerators().get(0).generate(random, status));
  }

  @Override
  public int numberOfNeededComponents() {
    return 1;
  }

  private int size(SourceOfRandomness random) {
    return sizeRange != null
        ? random.nextInt(sizeRange.min(), sizeRange.max())
        : random.nextInt(0, 100);
  }
}
