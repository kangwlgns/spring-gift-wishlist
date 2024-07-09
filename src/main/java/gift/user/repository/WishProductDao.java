package gift.user.repository;

import gift.user.dto.WishProductRequestDto;
import gift.user.dto.WishProductResponseDto;
import gift.user.entity.WishProductEntity;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishProductDao {

    private final JdbcTemplate jdbcTemplate;

    public WishProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 제품을 위시리스트에 넣는 함수. 개수는 무조건 1
    public boolean insertWishProduct(String email, WishProductRequestDto wishProductRequestDto) {
        long id = wishProductRequestDto.id();
        // 이미 있는 제품을 넣은 경우, false를 반환
        if (exists(email, id)) {
            return false;
        }

        WishProductEntity wishProductEntity = new WishProductEntity(email,
            wishProductRequestDto.id(),
            wishProductRequestDto.name(), wishProductRequestDto.price(),
            wishProductRequestDto.imageUrl(), 1);

        var sql = """
            insert into wish_products (email, id, name, price, image, quantity) values (?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(sql, wishProductEntity.getEmail(), wishProductEntity.getId(),
            wishProductEntity.getName(), wishProductEntity.getPrice(),
            wishProductEntity.getImageUrl(), wishProductEntity.getQuantity());

        return true;
    }

    // 개인 위시리스트에서 모든 제품을 꺼내오는 함수
    public List<WishProductResponseDto> selectWishProducts(String email) {
        var sql = """
            select email, id, name, price, image, quantity
            from wish_products
            where email = ?
            """;

        List<WishProductEntity> wishProductEntityList = jdbcTemplate.query(sql,
            (resultSet, rowNum) -> new WishProductEntity(
                resultSet.getString("email"), resultSet.getLong("id"),
                resultSet.getString("name"), resultSet.getInt("price"),
                resultSet.getString("image"), resultSet.getInt("quantity")
            ), email);

        return wishProductEntityList.stream().map(wishProductEntity -> new WishProductResponseDto(
            wishProductEntity.getId(), wishProductEntity.getName(), wishProductEntity.getPrice(),
            wishProductEntity.getImageUrl(), wishProductEntity.getQuantity()
        )).collect(Collectors.toList());
    }

    public WishProductResponseDto selectWishProduct(String email, long id) {
        verifyWishProductExist(email, id);

        var sql = """
            select email, id, name, price, image, quantity
            from wish_products
            where email = ? and id = ?
            """;

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new WishProductResponseDto(
            resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("price"),
            resultSet.getString("image"), resultSet.getInt("quantity")), email, id);
    }

    // email, id(pk)를 갖는 제품의 양을 변경. 일반 제품에 대한 수정 권한은 개수뿐이므로 전체를 수정하는 기능을 만들지는 않았습니다.
    public void updateWishProductQuantity(String email, long id, int quantity) {
        verifyWishProductExist(email, id);

        // 만약 수정하려고 하는 양이 0 이하라면, 제품을 삭제합니다.
        if (quantity <= 0) {
            deleteWishProduct(email, id);
            return;
        }

        // 그렇지 않다면 수정합니다.
        var sql = """
            update wish_products set quantity = ? where email = ? and id = ?;
            """;

        jdbcTemplate.update(sql, quantity, email, id);
    }

    // 제품을 제거하는 함수
    public void deleteWishProduct(String email, long id) {
        verifyWishProductExist(email, id);

        var sql = """
            delete from wish_products where email = ? and id = ?;
            """;

        jdbcTemplate.update(sql, email, id);
    }

    // db에서 특정 key를 갖는 로우가 있는지 확인하는 메서드
    private boolean exists(String email, long id) {
        var sql = """
            select email
            from wish_products
            where email = ? and id = ?;
            """;

        // 결과의 로우가 존재하는지 반환
        boolean isEmpty = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> resultSet.getString("email"), email, id)
            .isEmpty();

        return !isEmpty;
    }

    // 장바구니에서 수정 및 삭제를 하려는데 모종의 이유로 없는 경우.
    private void verifyWishProductExist(String email, long id) {
        if (!exists(email, id)) {
            throw new NoSuchElementException("이미 장바구니에 없는 제품입니다.");
        }
    }
}
