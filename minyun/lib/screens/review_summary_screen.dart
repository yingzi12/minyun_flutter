import 'package:flutter/material.dart';
import 'package:minyun/component/AppButton.dart';
import 'package:minyun/screens/user/account_screen.dart';
import 'package:minyun/utils/AppColors.dart';
import 'package:minyun/utils/AppCommon.dart';
import 'package:minyun/utils/AppContents.dart';

import '../utils/images.dart';
import 'bottom_navigation_bar_screen.dart';

class ReviewSummaryScreen extends StatelessWidget {
  ReviewSummaryScreen({Key? key, required this.months, required this.amount, required this.image, required this.paymentMethod}) : super(key: key);
  final String months;
  final String amount;
  final String image;
  final String paymentMethod;

  int count = 0;
  double tax = 1.99;

  @override
  Widget build(BuildContext context) {
    double total = double.parse(amount) + tax;

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Review Summary",
          style: appBoldTextStyle(fontSize: 24),
        ),
        elevation: 0,
        iconTheme: IconThemeData(color: mode.theme ? Colors.white : Colors.black),
      ),
      body: Stack(
        fit: StackFit.expand,
        children: [
          Column(
            children: [
              SizedBox(height: 16),
              Container(
                margin: EdgeInsets.symmetric(horizontal: 16),
                padding: EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200,
                  borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
                ),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Subscription plan", style: appSecondaryTextStyle(fontSize: 18)),
                        Text("$months months", style: appBoldTextStyle(fontSize: 18))
                      ],
                    ),
                    SizedBox(height: 16),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Amount", style: appSecondaryTextStyle(fontSize: 18)),
                        Text(amount, style: appBoldTextStyle(fontSize: 18)),
                      ],
                    ),
                    SizedBox(height: 16),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Tax", style: appSecondaryTextStyle(fontSize: 18)),
                        Text("\$$tax", style: appBoldTextStyle(fontSize: 18)),
                      ],
                    ),
                    SizedBox(height: 16),
                    Divider(),
                    SizedBox(height: 16),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Total Amount", style: appSecondaryTextStyle(fontSize: 18)),
                        Text("\$${total.toString().substring(0, 4)}", style: appBoldTextStyle(fontSize: 18)),
                      ],
                    ),
                    SizedBox(height: 16),
                  ],
                ),
              ),
              SizedBox(height: 16),
              Container(
                margin: EdgeInsets.symmetric(horizontal: 16),
                padding: EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: mode.theme ? darkPrimaryLightColor : Colors.grey.shade200,
                  borderRadius: BorderRadius.circular(DEFAULT_RADIUS),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text("Payment Method", style: appBoldTextStyle(fontSize: 18)),
                    SizedBox(height: 16),
                    Divider(),
                    ListTile(
                      contentPadding: EdgeInsets.zero,
                      title: Text(paymentMethod, style: appBoldTextStyle(fontSize: 18)),
                      leading: Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Image.asset(image, height: 36, width: 36),
                      ),
                      trailing: TextButton(
                        onPressed: () {
                          Navigator.pop(context);
                        },
                        child: Text(
                          "Change",
                          style: appPrimaryTextStyle(color: primaryColor),
                        ),
                      ),
                    ),
                  ],
                ),
              )
            ],
          ),
          Positioned(
            bottom: 16,
            right: 16,
            left: 16,
            child: AppButton(
              text: "Confirm Payment",
              onTap: () {
                showDialog(
                    context: context,
                    builder: (context) {
                      return AlertDialog(
                        backgroundColor: mode.theme ? darkPrimaryLightColor : Colors.white,
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(DEFAULT_RADIUS)),
                        content: Column(
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            SizedBox(height: 16),
                            CircleAvatar(
                              backgroundColor: primaryColor,
                              minRadius: 30,
                              maxRadius: 55,
                              child: Padding(
                                padding: EdgeInsets.all(32.0),
                                child: Image.asset(star_image, color: Colors.white),
                              ),
                            ),
                            SizedBox(height: 24),
                            Text(
                              "Welcome to Premium!",
                              textAlign: TextAlign.center,
                              style: appBoldTextStyle(color: primaryColor, fontSize: 20),
                            ),
                            SizedBox(height: 16),
                            Text("You have successfully subscribed for $months months premium. Enjoy the benefits.",
                                style: appSecondaryTextStyle(), textAlign: TextAlign.center),
                            SizedBox(height: 24),
                            AppButton(
                              text: "Ok",
                              onTap: () {
                                Navigator.push(context, MaterialPageRoute(builder: (context) => BottomNavigationBarScreen(itemIndex: 0)));
                              },
                            )
                          ],
                        ),
                      );
                    });
              },
            ),
          ),
        ],
      ),
    );
  }
}
