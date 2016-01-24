//
//  RuleTableViewCell.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RuleTableViewCell.h"

@implementation RuleTableViewCell

- (void)drawRect:(NSRect)dirtyRect {
    [super drawRect:dirtyRect];
}

- (void)setRuleString:(NSString *)ruleString {
    NSFont *txtFont = [NSFont fontWithName:@"DK Prince Frog" size:12];
    NSColor *txtColor = [NSColor whiteColor];
    NSMutableParagraphStyle *leftAligned = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    [leftAligned setAlignment:NSLeftTextAlignment];
    NSDictionary *txtDict = @{
                              NSFontAttributeName: txtFont,
                              NSForegroundColorAttributeName: txtColor,
                              NSParagraphStyleAttributeName: leftAligned
                              };
    NSAttributedString *attrStr = [[NSAttributedString alloc] initWithString:ruleString attributes:txtDict];
    [self.ruleText setAttributedStringValue:attrStr];
}

- (NSString *)getRuleString {
    return self.ruleText.stringValue;
}

@end
