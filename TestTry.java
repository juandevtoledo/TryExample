package com.lulobank.cards.v3.usecase;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class TestTry {

    public static void main(String... args){

        withEntity();


    }

    private static void withOption() {
        Try<MapEntity> tryR= Try.of(()->getEntityOption())
                .map(op-> getEnty(op));

        ResultTest resultTest=tryR
                .map(card->new ResultTest(card.getClientId(),""))
                .recover(NullPointerException.class,ex->new ResultTest("","nullPointer"))
                .getOrElse(()->new ResultTest("","empty"));

        System.out.println(resultTest.getClientId()+""+resultTest.getError());
    }

    private static MapEntity getEnty(Option<Entity> op) {
        return op.map(entity -> new MapEntity(entity.getClientId()))
                .getOrNull();
    }

    private static void withEntity() {
        Try<MapEntity> tryR=Try.of(()->getEntity())
                .map(entity -> {
                    return Objects.isNull(entity)?null:
                    new MapEntity(entity.getClientId());
                });

        ResultTest resultTest=tryR
                .filter(Objects::nonNull)
                .map(card->new ResultTest(card.getClientId(),""))
                .recover(NullPointerException.class,ex->new ResultTest("","nullPointer"))
                .getOrElse(()->new ResultTest("","empty"));
        System.out.println(resultTest.getClientId()+""+resultTest.getError());
    }

    static Option<Entity> getEntityOption() throws RuntimeException{
        Entity entity=new Entity("22222");
        return Option.none();
    }

    static Entity getEntity() throws RuntimeException{
        Entity entity=new Entity("22222");
        return null;
    }
}
@Getter
@Setter
@AllArgsConstructor
class Entity{
    private String clientId;
}

@Getter
@Setter
@AllArgsConstructor
class MapEntity{
    private String clientId;
}

@Getter
@Setter
@AllArgsConstructor
class ResultTest{
    private String clientId;
    private String error;
}

