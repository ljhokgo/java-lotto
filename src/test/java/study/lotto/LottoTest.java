package study.lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import study.lotto.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class LottoTest {

    @DisplayName("로또 번호 생성 테스트 - 성공")
    @ParameterizedTest
    @ValueSource(ints = { 1, 3, 5, 7, 23, 45 })
    void create_lotto_number_with_proper_values(int value) {
        assertThat(new LottoNumber(value))
                .isInstanceOf(LottoNumber.class);
    }

    @DisplayName("로또 번호 생성 테스트 - 실패")
    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 46, 80 })
    void create_lotto_number_with_improper_values(int value) {
        assertThatThrownBy(() -> new LottoNumber(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 생성 테스트")
    @Test
    void generate_lotto() {
        Lotto lotto = Lotto.generate();

        assertThat(lotto.getLottoNumbers())
                .allMatch(lottoNumber -> lottoNumber.getNumber()>0 && lottoNumber.getNumber()<46);

        assertThat(lotto.getLottoNumbers().size())
                .isEqualTo(6);
    }

    @DisplayName("당첨 번호 대조 테스트")
    @ParameterizedTest
    @ValueSource(strings = { "1, 2, 3, 4, 5, 6", "2, 3, 4, 7, 9, 12" })
    void compare_lotto_to_winning_numbers(String input) {
        List<LottoNumber> lottoNumbers = Stream.of(1, 3, 5, 7, 9, 11).map(LottoNumber::new).collect(Collectors.toList());
        Lotto lotto = new Lotto(lottoNumbers);

        String[] split = input.split(", ");
        WinningLotto winningLotto = WinningLotto.convertToWinningLotto(split);

        assertThat(lotto.compareToWinningNumbers(winningLotto))
                .isEqualTo(3);
    }

    @DisplayName("로또 여러개 동시 생성 테스트")
    @Test
    void create_lotto_list() {
        LottoList lottoList = LottoList.create(4);

        assertThat(lottoList.getLottoList().size()).isEqualTo(4);

        assertThat(lottoList.getLottoList())
                .allMatch(Objects::nonNull);
    }

    @DisplayName("로또 당첨금 계산")
    @ParameterizedTest
    @ValueSource(strings = { "1, 2, 3, 4, 5, 6", "2, 3, 4, 7, 9, 12" })
    void check_prize(String input) {
        List<LottoNumber> lottoNumbers = Stream.of(1, 3, 5, 7, 9, 11).map(LottoNumber::new).collect(Collectors.toList());
        Lotto lotto = new Lotto(lottoNumbers);

        String[] split = input.split(", ");
        WinningLotto winningLotto = WinningLotto.convertToWinningLotto(split);

        LottoRank lottoRank = lotto.getPrize(winningLotto);

        assertThat(lottoRank.getPrize())
                .isEqualTo(5000);
    }

    @DisplayName("수익률 계산")
    @Test
    void calculate_earning_rate() {
        LottoList lottoList = LottoList.create(10);

        String[] split = "1, 2, 3, 4, 5, 6".split(", ");
        WinningLotto winningLotto = WinningLotto.convertToWinningLotto(split);
        Statistics statistics = Statistics.createStatistics(4000, lottoList, winningLotto);

        assertThat(statistics.getEarningRate())
                .isGreaterThanOrEqualTo(0);
    }
}
