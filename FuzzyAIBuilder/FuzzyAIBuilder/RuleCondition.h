//
//  RuleCondition.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RuleCondition : NSObject

@property (strong, nonatomic, readonly) NSDictionary* conditions;

//@property NSString* condString;
@property NSString* condCode;
//@property NSString* valueString;
@property NSString* valueCode;

- (instancetype) init;
- (instancetype) initWithCode:(NSString *)code;
- (instancetype) initWithCode:(NSString *)code value:(NSString *)valueCode;
- (void)setCondValue: (NSString *)value;
- (BOOL)isSet;
- (NSArray *)getPossibleValues;
+ (NSArray *)getPossibleConditions;
+ (NSArray *)getPossibleValueForCond:(NSString *)cond;

@end
