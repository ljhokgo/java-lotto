package study.lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.lotto.model.LottoList;
import study.lotto.model.Statistics;
import study.lotto.model.WinningLotto;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class StatisticsTest {

    @DisplayName("수익률 계산")
    @Test
    void calculate_earning_rate() {
        LottoList lottoList = LottoList.create(10);

        String[] split = "1, 2, 3, 4, 5, 6".split(", ");
        WinningLotto winningLotto = WinningLotto.convertToWinningLotto(split);
        Statistics statistics = new Statistics(lottoList);
        statistics.calculateStatistics(winningLotto);

        assertThat(statistics.calculateEarningRate(14000))
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);
    }
}
