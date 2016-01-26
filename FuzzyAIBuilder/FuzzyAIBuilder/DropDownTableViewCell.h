//
//  DropDownTableViewCell.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "RuleCondition.h"
#import "RulePopUpButton.h"

@interface DropDownTableViewCell : NSTableCellView

@property (weak) IBOutlet NSImageView *background;
@property (weak) IBOutlet RulePopUpButton *popUp;

- (NSInteger)type;
- (NSInteger)getRow;
- (void)setType:(NSInteger)column;
- (void)setString:(NSString *)string;
- (NSString *)getString;
- (void)disableValueButton;
- (void)enableValueButtonForCond:(NSString *)cond;
+ (NSAttributedString *)changeToWhiteText:(NSString *)text withSize:(int)size;
+ (NSAttributedString *)changeToBlackText:(NSString *)text withSize:(int)size;
@end
