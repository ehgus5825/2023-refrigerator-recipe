package refrigerator.back.myscore.application.port.out;


import refrigerator.back.myscore.application.domain.MyScore;

public interface SaveMyScorePort {
    Long save(MyScore score);
}
