package study.lotto.view;

import study.lotto.model.LottoRank;
import study.lotto.model.Lottos;
import study.lotto.model.Statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class ResultView {
    private static final int EARNING_RATE_SCALE = 2;
    private static final String LOTTO_RANK_STRING_FORMAT_BASIC = "%d개 일치 (%d원)- %d개";
    private static final String LOTTO_RANK_STRING_FORMAT_SECOND = "%d개 일치, 보너스 볼 일치 (%d원)- %d개";

    public static void printPurchaseMessage(int numOfManualLottos, int numOfAutoLottos, Lottos lottos) {
        printEmptyLine();
        System.out.println(String.format("수동으로 %d매, 자동으로 %d매를 구매했습니다.", numOfManualLottos, numOfAutoLottos));

        printLottos(lottos);
        printEmptyLine();
    }

    public static void printStatisticsMessage(Statistics statistics, BigDecimal earningRate) {
        printEmptyLine();
        printStatistics(statistics);
        printEarningRate(earningRate);
    }

    private static void printLottos(Lottos lottos) {
        System.out.println(lottos);
    }

    private static void printStatistics(Statistics statistics) {
        System.out.println("당첨 통계");
        System.out.println("----------");

        LottoRank.getLottoRanksOverMinimumMatches()
                .forEach(lottoRank ->
                    System.out.println(
                            String.format(lottoRank.equals(LottoRank.SECOND_RANK) ?
                                            LOTTO_RANK_STRING_FORMAT_SECOND : LOTTO_RANK_STRING_FORMAT_BASIC,
                                    lottoRank.getMatches(),
                                    lottoRank.getPrize(),
                                    statistics.getStatistics().getOrDefault(lottoRank, 0)))
                );
    }

    private static void printEarningRate(BigDecimal earningRate) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(EARNING_RATE_SCALE);
        format.setRoundingMode(RoundingMode.HALF_EVEN);

        System.out.println(String.format("총 수익률은 %s 입니다.", format.format(earningRate)));
    }

    private static void printEmptyLine() {
        System.out.println();
    }
}
