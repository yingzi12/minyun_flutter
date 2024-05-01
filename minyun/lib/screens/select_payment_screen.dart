import 'package:flutter/material.dart';
import 'package:minyun/component/AppButton.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/screens/review_summary_screen.dart';
import 'package:minyun/utils/AppColors.dart';

import '../models/select_payment_screen_model.dart';
import '../utils/AppCommon.dart';
import '../utils/images.dart';
import 'add_new_payment_screen.dart';

class SelectPaymentScreen extends StatefulWidget {
  const SelectPaymentScreen({Key? key, required this.amount, required this.months}) : super(key: key);
  final String months;
  final String amount;

  @override
  State<SelectPaymentScreen> createState() => _SelectPaymentScreenState();
}

class _SelectPaymentScreenState extends State<SelectPaymentScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Select Payment Method"),
        titleTextStyle: appMainBoldTextStyle(fontSize: 24),
        elevation: 0,
        iconTheme: IconThemeData(color: mode.theme ? Colors.white : Colors.black),
        actions: [
          IconButton(
            splashRadius: 22,
            onPressed: () {},
            icon: Image.asset(scan_image, height: 26, width: 26, color: mode.theme ? Colors.white : Colors.black),
          ),
        ],
      ),
      body: Stack(
        children: [
          SingleChildScrollView(
            padding: EdgeInsets.only(bottom: 70),
            child: Column(
              children: [
                ListView.builder(
                  primary: false,
                  padding: EdgeInsets.zero,
                  itemCount: paymentOptions.length,
                  shrinkWrap: true,
                  itemBuilder: (context, int index) {
                    return InkWell(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ReviewSummaryScreen(
                              months: widget.months,
                              amount: widget.amount,
                              image: paymentOptions[index].image!,
                              paymentMethod: paymentOptions[index].title!,
                            ),
                          ),
                        );
                      },
                      child: ListTile(
                        contentPadding: EdgeInsets.only(left: 8),
                        title: Text(paymentOptions[index].title ?? "", style: appMainBoldTextStyle(fontSize: 18)),
                        leading: Padding(
                          padding: EdgeInsets.all(8.0),
                          child: Image.asset(paymentOptions[index].image ?? "", height: 35, width: 35),
                        ),
                        trailing: IconButton(
                            onPressed: () {}, icon: Icon(Icons.arrow_forward_ios_rounded, color: mode.theme ? Colors.white : Colors.black, size: 18)),
                      ),
                    );
                  },
                ),
              ],
            ),
          ),
          Positioned(
            bottom: 16,
            right: 16,
            left: 16,
            child: AppButton(
              text: "+ Add New Payment",
              onTap: () {
                Navigator.push(context, MaterialPageRoute(builder: (context) => AddNewPaymentScreen())).then((_) => setState(() {}));
              },
              color: mode.theme ? darkPrimaryColor : primaryLightColor,
              textColor: mode.theme ? Colors.white : primaryColor,
            ),
          ),
        ],
      ),
    );
  }
}
