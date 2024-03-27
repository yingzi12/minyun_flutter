
const baseUrl="https://admin.aiavr.uk";
// const baseUrl="https://test.aiavr.uk";
// const baseUrl="http://192.168.68.100:8098";
const SOURCEWEB="https://image.51x.uk/xinshijie";
const paymentUrl="https://www.aiavr.uk/paypalModile";
// const paymentUrl="http://192.168.68.106:3000/paypalModile";

const Map<String, String> hostnameValues = {
  'video.': 'video',
  'hotfirl': 'hotfirl',
  'https://images.hotgirl.asia': 'https://imageshotgirl.yappgcu.uk',
  'https://mogura.my.id': 'https://mogura.yappgcu.uk',
  'jkforum': 'jkforum.com',
  'x60': 'x60.com'
};

Map<String, String> AlbumChargeMap = {
  '免费': '1',
  'VIP免费': '2',
  'VIP折扣': '3',
  'VIP独享': '4',
  '统一': '5',
};

Map<int, String> AlbumChargeTypeMap = {
  1: '免费',
  2: 'VIP免费',
  3: 'VIP折扣',
  4: 'VIP独享',
  5: '统一',
};

Map<String, String> VipTimeTypeMap = {
  '天': '1',
  '周': '2',
  '月': '3',
  '年': '4',
  // '永久': '5',
};
Map<int, String> VipTimeMap = {
  1: '天',
  2: '周',
  3: '月',
  4: '年',
  // 5: '永久',
};

Map<int, String> SystemVipMap = {
  1: '月度会员',
  2: '季度会员',
  3: '半年会员',
  4: '年度会员',
  5: '永久',
};